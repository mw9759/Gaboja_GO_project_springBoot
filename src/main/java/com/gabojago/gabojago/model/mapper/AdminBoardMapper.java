package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.AdminBoardDto;

@Mapper
public interface AdminBoardMapper {

	boolean writeArticle(AdminBoardDto boardDto) throws SQLException;
	List<AdminBoardDto> listArticle() throws SQLException;
	AdminBoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	boolean modifyArticle(AdminBoardDto boardDto) throws SQLException;
	boolean deleteArticle(int articleNO) throws SQLException;
	
}
