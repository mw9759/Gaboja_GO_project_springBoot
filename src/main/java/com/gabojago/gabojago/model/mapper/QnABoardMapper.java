package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.QnAboardDto;

@Mapper
public interface QnABoardMapper {

	boolean writeArticle(QnAboardDto boardDto) throws SQLException;
	List<QnAboardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalQnaBoardCount(Map<String, Object> map) throws SQLException;
	QnAboardDto getArticle(int articleNo) throws SQLException;
	boolean modifyArticle(QnAboardDto boardDto) throws SQLException;
	boolean deleteArticle(int articleNO) throws SQLException;
	boolean answerOk(int articleNo) throws SQLException;
	//qnaresult
	List<QnAboardDto> listArticler(BoardParameterDto boardParameterDto);
	boolean writeArticler() throws SQLException;
	boolean deleteArticler(int articleNO) throws SQLException;
	boolean modifyArticler(QnAboardDto boardDto) throws SQLException;
	boolean updateIsAnswered(int articleNo) throws Exception;
	boolean updateNoAnswered(int articleNo) throws Exception;
}
