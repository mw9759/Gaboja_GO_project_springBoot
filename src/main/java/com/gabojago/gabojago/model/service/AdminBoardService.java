package com.gabojago.gabojago.model.service;

import java.util.List;

import com.gabojago.gabojago.model.dto.AdminBoardDto;


public interface AdminBoardService {

	boolean writeArticle(AdminBoardDto boardDto) throws Exception;
	List<AdminBoardDto> listArticle() throws Exception;
	AdminBoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyArticle(AdminBoardDto boardDto) throws Exception;
	boolean deleteArticle(int articleNo) throws Exception;
	
}
