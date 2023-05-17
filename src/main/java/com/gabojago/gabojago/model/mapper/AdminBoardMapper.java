package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;

@Mapper
public interface AdminBoardMapper {

	boolean writeArticle(AdminBoardDto boardDto) throws SQLException;
	List<AdminBoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalAdminBoardCount(Map<String, Object> map) throws SQLException;
	AdminBoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	boolean modifyArticle(AdminBoardDto boardDto) throws SQLException;
	boolean deleteArticle(int articleNO) throws SQLException;
	
}
