package com.gabojago.gabojago.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.ImgInfos;
import com.gabojago.gabojago.model.dto.MemberDto;

@Mapper
public interface MemberMapper {

	int idCheck(String userId) throws SQLException;
	boolean joinMember(MemberDto memberDto) throws SQLException;
	String findId(Map<String, String> map) throws SQLException;
	MemberDto loginMember(Map<String, String> map) throws SQLException;
	String findPwd(Map<String, String> map) throws SQLException;
	MemberDto showMyInfo(String userid)throws SQLException;
	int rmAccount(String userid)throws SQLException;
	int updateInfo(Map<String, String> map)throws SQLException;
	List<UserBoardDto> getmyBoards(String userid)throws SQLException;
	String findSalt(String userId) throws SQLException;
	void modifyPwd(Map<String, String> map) throws SQLException;
	int checkPwd(Map<String, String> map) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	List<ImgInfos> getMyImgs(String userid) throws Exception;
	boolean modifyProfile(Map<String, String> map) throws Exception;
}