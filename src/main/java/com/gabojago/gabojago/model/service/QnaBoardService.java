package com.gabojago.gabojago.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.QnAboardDto;

public interface QnaBoardService {

	boolean writeArticle(QnAboardDto boardDto) throws Exception;
	List<QnAboardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	QnAboardDto getArticle(int articleNo) throws Exception;
	boolean answerOk(int articleNo) throws SQLException;
	boolean modifyArticle(QnAboardDto boardDto) throws Exception;
	boolean deleteArticle(int articleNo) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	//qnarstult
	List<QnAboardDto> listArticler(BoardParameterDto boardParameterDto) throws Exception;
	boolean writeArticler() throws Exception;
	boolean deleteArticler(int articleNo) throws Exception;
	boolean modifyArticler(QnAboardDto boardDto) throws Exception;
	boolean updateIsAnswered(int articleNo) throws Exception;
	boolean updateNoAnswered(int articleNo) throws Exception;
}
