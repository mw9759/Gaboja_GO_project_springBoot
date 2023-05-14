package com.gabojago.gabojago.model.service;

import java.util.List;
import java.util.Queue;

import com.gabojago.gabojago.model.dto.TripSearchDto;

public interface TripSearchService {
	
	List<TripSearchDto> tripList(int sido, int contentTypeId, String keyword, int sortType, double latitude, double longitude) throws Exception;
	Queue<TripSearchDto> distArticle(List<TripSearchDto> list) throws Exception;	
	
}
