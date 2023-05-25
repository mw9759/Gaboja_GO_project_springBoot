package com.gabojago.gabojago.model.service;

import java.util.List;
import java.util.Map;

import com.gabojago.gabojago.model.dto.AttractionCommentDto;
import com.gabojago.gabojago.model.dto.TravelPlanDto;

public interface TravelPlanService {
	
	List<String> planList(String userId) throws Exception;
	void createPlan(TravelPlanDto travelPlanDto) throws Exception;
	void removePlan(TravelPlanDto travelPlanDto) throws Exception;
	void savePlan(TravelPlanDto travelPlanDto) throws Exception;
	String loadPlan(TravelPlanDto travelPlanDto) throws Exception;
	
	// Comment
	List<AttractionCommentDto> getComments(int contentId) throws Exception;
	boolean writeComment(AttractionCommentDto comment) throws Exception;
	boolean deleteComment(int commentId) throws Exception;
	boolean modifyComment(AttractionCommentDto comment) throws Exception;;
	boolean updateLike(Map<String, String> map) throws Exception;
	void myLike(Map<String, String> map) throws Exception;
	
}
