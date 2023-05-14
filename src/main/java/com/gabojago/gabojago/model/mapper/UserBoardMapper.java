package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.UserBoardDto;
@Mapper
public interface UserBoardMapper {

	void writeArticle(UserBoardDto userboardDto) throws SQLException;

	List<UserBoardDto> listArticle() throws SQLException;

	UserBoardDto getArticle(int articleNo)throws SQLException;

	void updateHit(int articleNo)throws SQLException;

	void modifyArticle(UserBoardDto boardDto)throws SQLException;

	void deleteArticle(int articleNo)throws SQLException;
	
}
