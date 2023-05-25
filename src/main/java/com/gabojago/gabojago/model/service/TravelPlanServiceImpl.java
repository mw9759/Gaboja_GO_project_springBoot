package com.gabojago.gabojago.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.AttractionCommentDto;
import com.gabojago.gabojago.model.dto.TravelPlanDto;
import com.gabojago.gabojago.model.mapper.TravelPlanMapper;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

	private TravelPlanMapper travelPlanMapper;

	public TravelPlanServiceImpl(TravelPlanMapper travelPlanMapper) {
		super();
		this.travelPlanMapper = travelPlanMapper;
	}

	@Override
	public List<String> planList(String userId) throws Exception {
		return travelPlanMapper.planList(userId);
	}

	@Override
	public void createPlan(TravelPlanDto travelPlanDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", travelPlanDto.getUserId());
		map.put("planName", travelPlanDto.getPlanName());
		travelPlanMapper.createPlan(map);
	}

	@Override
	public void removePlan(TravelPlanDto travelPlanDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", travelPlanDto.getUserId());
		map.put("planName", travelPlanDto.getPlanName());
		travelPlanMapper.removePlan(map);
	}

	@Override
	public void savePlan(TravelPlanDto travelPlanDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", travelPlanDto.getUserId());
		map.put("planName", travelPlanDto.getPlanName());
		map.put("content", travelPlanDto.getContent());
		travelPlanMapper.savePlan(map);
	}

	@Override
	public String loadPlan(TravelPlanDto travelPlanDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", travelPlanDto.getUserId());
		map.put("planName", travelPlanDto.getPlanName());
		return travelPlanMapper.loadPlan(map);
	}

	@Override
	public List<AttractionCommentDto> getComments(int contentId) throws Exception {
		return travelPlanMapper.getComments(contentId);
	}

	@Override
	public boolean writeComment(AttractionCommentDto comment) throws Exception {
		return travelPlanMapper.writeComment(comment);
	}

	@Override
	public boolean deleteComment(int commentId) throws Exception {
		return travelPlanMapper.deleteComment(commentId);
	}

	@Override
	public boolean modifyComment(AttractionCommentDto comment) throws Exception {
		return travelPlanMapper.modifyComment(comment);
	}
	
	@Override
	public boolean updateLike(Map<String, String> map) throws Exception {
		System.out.println(map.get("like") + " " + map.get("contentId"));
		System.out.println(map.get("likeAttractions"));
		return travelPlanMapper.updateLike(map);
	}
	
	@Override
	public void myLike(Map<String, String> map) throws Exception {
		System.out.println(1);
		System.out.println(map.get("likeAttractions"));
		System.out.println(map.get("userId"));
		travelPlanMapper.myLike(map);
	}

}
