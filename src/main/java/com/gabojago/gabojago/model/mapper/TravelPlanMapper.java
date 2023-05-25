package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.AttractionCommentDto;

@Mapper
public interface TravelPlanMapper {
	
	List<String> planList(String userId) throws SQLException;
	void createPlan(Map<String, String> map) throws Exception;
	void removePlan(Map<String, String> map) throws Exception;
	void savePlan(Map<String, String> map) throws Exception;
	String loadPlan(Map<String, String> map) throws Exception;
	
	// Comment
	List<AttractionCommentDto> getComments(int contentId) throws Exception;
	boolean writeComment(AttractionCommentDto comment) throws Exception;
	boolean deleteComment(int commentId) throws Exception;
	boolean modifyComment(AttractionCommentDto comment) throws Exception;
	boolean updateLike(Map<String, String> map) throws Exception;
	void myLike(Map<String, String> map) throws Exception;
	
}