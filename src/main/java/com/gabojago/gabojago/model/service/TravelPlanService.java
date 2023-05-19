package com.gabojago.gabojago.model.service;

import java.util.List;

import com.gabojago.gabojago.model.dto.TravelPlanDto;

public interface TravelPlanService {
	
	List<String> planList(String userId) throws Exception;
	void createPlan(TravelPlanDto travelPlanDto) throws Exception;
	
}
