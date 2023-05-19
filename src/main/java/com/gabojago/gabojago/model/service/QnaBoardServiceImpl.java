package com.gabojago.gabojago.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.QnAboardDto;
import com.gabojago.gabojago.model.mapper.QnABoardMapper;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {
	
	private QnABoardMapper qnaboardMapper;
	
	public QnaBoardServiceImpl(QnABoardMapper qnaboardMapper) {
		super();
		this.qnaboardMapper = qnaboardMapper;
	}

	@Override
	public boolean writeArticle(QnAboardDto boardDto) throws Exception {
		return qnaboardMapper.writeArticle(boardDto);
	}
	@Override
	public boolean writeArticler() throws Exception {
		return qnaboardMapper.writeArticler();
	}
	@Override
	public List<QnAboardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return qnaboardMapper.listArticle(boardParameterDto);
	}
	
	@Override
	public List<QnAboardDto> listArticler(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return qnaboardMapper.listArticler(boardParameterDto);
	}
	
//	@Override
//	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
//		PageNavigation pageNavigation = new PageNavigation();
//
//		int naviSize = SizeConstant.NAVIGATION_SIZE;
//		int sizePerPage = SizeConstant.LIST_SIZE;
//		int currentPage = Integer.parseInt(map.get("pgno"));
//
//		pageNavigation.setCurrentPage(currentPage);
//		pageNavigation.setNaviSize(naviSize);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
//		param.put("key", key == null ? "" : key);
//		param.put("word", map.get("word") == null ? "" : map.get("word"));
//		int totalCount = adminBoardMapper.getTotalAdminBoardCount(param);
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = currentPage <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//
//		return pageNavigation;
//	}

	@Override
	public QnAboardDto getArticle(int articleNo) throws Exception {
		return qnaboardMapper.getArticle(articleNo);
	}


	@Override
	public boolean modifyArticle(QnAboardDto boardDto) throws Exception {
		return qnaboardMapper.modifyArticle(boardDto);
	}
	
	@Override
	public boolean modifyArticler(QnAboardDto boardDto) throws Exception {
		return qnaboardMapper.modifyArticler(boardDto);
	}
	

	@Override
	public boolean deleteArticle(int articleNo) throws Exception {
		return qnaboardMapper.deleteArticle(articleNo);
	}
	@Override
	public boolean deleteArticler(int articleNo) throws Exception {
		return qnaboardMapper.deleteArticler(articleNo);
	}
	@Override
	public int getNum(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = qnaboardMapper.getTotalQnaBoardCount(param);
		
		return totalCount;
	}

	@Override
	public boolean answerOk(int articleNo) throws SQLException {
		return qnaboardMapper.answerOk(articleNo);
	}
	
	@Override
	public boolean updateIsAnswered(int articleNo) throws Exception {
		return qnaboardMapper.updateIsAnswered(articleNo);
	}
	@Override
	public boolean updateNoAnswered(int articleNo) throws Exception {
		return qnaboardMapper.updateNoAnswered(articleNo);
	}
}
