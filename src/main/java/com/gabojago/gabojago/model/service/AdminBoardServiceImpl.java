package com.gabojago.gabojago.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.mapper.AdminBoardMapper;

@Service
public class AdminBoardServiceImpl implements AdminBoardService {
	
	private AdminBoardMapper adminboardMapper;
	
	public AdminBoardServiceImpl(AdminBoardMapper adminboardMapper) {
		super();
		this.adminboardMapper = adminboardMapper;
	}

	@Override
	public boolean writeArticle(AdminBoardDto boardDto) throws Exception {
		return adminboardMapper.writeArticle(boardDto);
	}

	@Override
	public List<AdminBoardDto> listArticle() throws Exception {
		return adminboardMapper.listArticle();
	}

	@Override
	public AdminBoardDto getArticle(int articleNo) throws Exception {
		return adminboardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		adminboardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyArticle(AdminBoardDto boardDto) throws Exception {
		return adminboardMapper.modifyArticle(boardDto);
	}

	@Override
	public boolean deleteArticle(int articleNo) throws Exception {
		return adminboardMapper.deleteArticle(articleNo);
		
	}

}
