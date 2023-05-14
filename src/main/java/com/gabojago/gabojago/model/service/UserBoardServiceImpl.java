package com.gabojago.gabojago.model.service;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

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
	public List<UserBoardDto> listArticle() throws SQLException {
		return userBoardMapper.listArticle();
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		userBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean deleteArticle(int articleNo) throws SQLException {
		return userBoardMapper.deleteArticle(articleNo);
	}


}
