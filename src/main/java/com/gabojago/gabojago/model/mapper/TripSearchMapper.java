package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.TripSearchDto;

@Mapper
public interface TripSearchMapper {
	
	List<TripSearchDto> tripList(Map<String, Object> map) throws SQLException;
	
}