package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.ImgInfos;
import com.gabojago.gabojago.model.dto.UserBoardCommentsDto;
import com.gabojago.gabojago.model.dto.UserBoardDto;
@Mapper
public interface UserBoardMapper {

	boolean writeArticle(UserBoardDto userboardDto) throws SQLException;

	List<UserBoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;

	UserBoardDto getArticle(int articleNo)throws SQLException;

	void updateHit(int articleNo)throws SQLException;

	boolean modifyArticle(UserBoardDto boardDto)throws SQLException;

	boolean deleteArticle(int articleNo)throws SQLException;
	
	int getTotalAdminBoardCount(Map<String, Object> map) throws SQLException;
	
	boolean registImgs(String imgs) throws Exception;
	
	List<ImgInfos> getImgs(ImgInfos imgs) throws Exception;
	
	boolean deleteImgs(int articleNo) throws Exception;
	
	boolean modifyImg(ImgInfos imgInfos) throws Exception;
	
	// 댓글관련
	List<UserBoardCommentsDto> getComments(int articleNo) throws Exception;

	boolean writeComment(UserBoardCommentsDto comment) throws Exception;
	
	void updateCommentsCnt(int articleNo) throws Exception;
	
	boolean deleteComment(int commentNo)throws Exception;
	
	boolean modifyComment(UserBoardCommentsDto comment);
}
