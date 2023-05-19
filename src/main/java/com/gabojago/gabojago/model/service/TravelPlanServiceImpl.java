package com.gabojago.gabojago.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
}
