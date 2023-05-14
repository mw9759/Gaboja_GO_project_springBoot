package com.gabojago.gabojago.model.service;


import java.sql.SQLException;
import java.util.List;

import com.gabojago.gabojago.model.dto.UserBoardDto;


public interface UserBoardService {

	void writeArticle(UserBoardDto userboardDto) throws SQLException;

	List<UserBoardDto> listArticle() throws SQLException;

	UserBoardDto getArticle(int articleNo)throws SQLException;

	void updateHit(int articleNo)throws SQLException;

	void modifyArticle(UserBoardDto boardDto)throws SQLException;

	void deleteArticle(int articleNo)throws SQLException;

	
	
}
