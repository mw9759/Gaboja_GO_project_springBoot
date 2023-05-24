package com.gabojago.gabojago.model.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.ImgInfos;
import com.gabojago.gabojago.model.dto.UserBoardCommentsDto;
import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.mapper.UserBoardMapper;

@Service
public class UserBoardServiceImpl implements UserBoardService{
	
	private UserBoardMapper userBoardMapper;

	public UserBoardServiceImpl(UserBoardMapper userBoardMapper) {
		super();
		this.userBoardMapper = userBoardMapper;
	}
	
	@Override
	public boolean writeArticle(UserBoardDto userboardDto) throws SQLException {
		return userBoardMapper.writeArticle(userboardDto);
	}

	@Override
	public UserBoardDto getArticle(int articleNo) throws SQLException {
		return userBoardMapper.getArticle(articleNo);
	}

	@Override
	public boolean modifyArticle(UserBoardDto boardDto) throws SQLException {
		return userBoardMapper.modifyArticle(boardDto);
	}

	@Override
	public List<UserBoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return userBoardMapper.listArticle(boardParameterDto);
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		userBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean deleteArticle(int articleNo) throws SQLException {
		return userBoardMapper.deleteArticle(articleNo);
	}

	@Override
	public int getNum(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key)) key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = userBoardMapper.getTotalAdminBoardCount(param);
		
		return totalCount;
	}

	@Override
	public boolean registImgs(String imgs) throws Exception {
		return userBoardMapper.registImgs(imgs);
	}

	@Override
	public List<ImgInfos> getImgs(ImgInfos imgs) throws Exception {
		return userBoardMapper.getImgs(imgs);
	}

	@Override
	public boolean deleteImgs(int articleNo) throws Exception {
		return userBoardMapper.deleteImgs(articleNo);
	}

	@Override
	public boolean modifyImg(ImgInfos imgInfos) throws Exception {
		return userBoardMapper.modifyImg(imgInfos);
	}
	
	// 댓글관련
	@Override
	public List<UserBoardCommentsDto> getComments(int articleNo) throws Exception {
		return userBoardMapper.getComments(articleNo);
	}

	@Override
	public boolean writeComment(UserBoardCommentsDto comment) throws Exception {
		return userBoardMapper.writeComment(comment);
	}

	@Override
	public void updateCommentsCnt(int articleNo) throws Exception {
		userBoardMapper.updateCommentsCnt(articleNo);
	}

	@Override
	public boolean deleteComment(int commentNo) throws Exception {
		return userBoardMapper.deleteComment(commentNo);
	}

	@Override
	public boolean modifyComment(UserBoardCommentsDto comment) {
		return userBoardMapper.modifyComment(comment);
	}
}
