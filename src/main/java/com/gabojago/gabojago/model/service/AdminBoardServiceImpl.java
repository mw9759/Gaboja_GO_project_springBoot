package com.gabojago.gabojago.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.mapper.AdminBoardMapper;

@Service
public class AdminBoardServiceImpl implements AdminBoardService {
	
	private AdminBoardMapper adminBoardMapper;
	
	public AdminBoardServiceImpl(AdminBoardMapper adminboardMapper) {
		super();
		this.adminBoardMapper = adminboardMapper;
	}

	@Override
	public boolean writeArticle(AdminBoardDto boardDto) throws Exception {
		return adminBoardMapper.writeArticle(boardDto);
	}

	@Override
	public List<AdminBoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return adminBoardMapper.listArticle(boardParameterDto);
	}

	@Override
	public AdminBoardDto getArticle(int articleNo) throws Exception {
		return adminBoardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		adminBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyArticle(AdminBoardDto boardDto) throws Exception {
		return adminBoardMapper.modifyArticle(boardDto);
	}

	@Override
	public boolean deleteArticle(int articleNo) throws Exception {
		return adminBoardMapper.deleteArticle(articleNo);
	}
	
	@Override
	public int getNum(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key)) key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = adminBoardMapper.getTotalAdminBoardCount(param);
		
		return totalCount;
	}

}
