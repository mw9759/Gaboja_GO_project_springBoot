package com.gabojago.gabojago.model.service;

import java.util.List;
import java.util.Map;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;

public interface AdminBoardService {

	boolean writeArticle(AdminBoardDto boardDto) throws Exception;
	List<AdminBoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	//PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	AdminBoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyArticle(AdminBoardDto boardDto) throws Exception;
	boolean deleteArticle(int articleNo) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
