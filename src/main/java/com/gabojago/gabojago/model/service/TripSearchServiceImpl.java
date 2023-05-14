package com.gabojago.gabojago.model.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.TripSearchDto;
import com.gabojago.gabojago.model.mapper.TripSearchMapper;

@Service
public class TripSearchServiceImpl implements TripSearchService {

	private TripSearchMapper tripSearchMapper;

	public TripSearchServiceImpl(TripSearchMapper tripSearchMapper) {
		super();
		this.tripSearchMapper = tripSearchMapper;
	}

	@Override
	public List<TripSearchDto> tripList(int sido, int contentTypeId, String keyword, int sortType, double latitude, double longitude) throws Exception {
		// 파라미터 설정
		HashMap<String, Object> map = new HashMap<>();
		map.put("sido", sido);
		map.put("contentTypeId", contentTypeId);
		map.put("keyword", keyword);
		
		// DB
		List<TripSearchDto> list = tripSearchMapper.tripList(map);
		
		// 거리정보 추가
		for (TripSearchDto board : list) {
			double value = calDistance(board.getLatitude(), board.getLongitude(), latitude, longitude) / 1000.0;
			value = Math.round(value * 100) / 100.0;
			board.setDistance(value);
		}
		
		// 정렬 알고리즘
		if (sortType == 1) {
			return list;
		} else {
			Queue<TripSearchDto> queue = distArticle(list);
			list = new ArrayList<>();
			while (!queue.isEmpty()) {
				list.add(queue.poll());
			}
			return list;
		}
	}

	@Override
	public Queue<TripSearchDto> distArticle(List<TripSearchDto> list) throws Exception {
		Queue<TripSearchDto> queue = new PriorityQueue<>(new Comparator<TripSearchDto>() {

			@Override
			public int compare(TripSearchDto o1, TripSearchDto o2) {
				int value = 0;
				if ((o1.getDistance() - o2.getDistance()) > 0) {
					value = 1;
				} else {
					value = -1;
				}
				return value;
			}

		});

		for (TripSearchDto board : list) {
			queue.offer(board);
		}

		return queue;
	}

	public double calDistance(double lat1, double lon1, double lat2, double lon2) {

		double theta, dist;
		theta = lon1 - lon2;
		dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);

		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; // 단위 mile 에서 km 변환.
		dist = dist * 1000.0;   // 단위 km 에서 m 로 변환

		return dist;
	}

	// 주어진 도(degree) 값을 라디언으로 변환
	private double deg2rad(double deg) {
		return (double) (deg * Math.PI / (double) 180d);
	}

	// 주어진 라디언 값을 도(degree)으로 변환
	private double rad2deg(double rad) {
		return (double) (rad * (double) 180d / Math.PI);
	}

}
