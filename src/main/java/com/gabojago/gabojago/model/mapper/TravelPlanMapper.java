package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelPlanMapper {
	
	List<String> planList(String userId) throws SQLException;
	void createPlan(Map<String, String> map) throws Exception;
	
}