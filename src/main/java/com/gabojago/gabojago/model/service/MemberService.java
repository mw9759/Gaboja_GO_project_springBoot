package com.gabojago.gabojago.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;


public interface MemberService {

	int idCheck(String userId) throws Exception;
	boolean joinMember(MemberDto memberDto) throws Exception;
	String findId(Map<String, String> map) throws Exception;
	String findPwd(Map<String, String> map) throws Exception;
	MemberDto loginMember(Map<String, String> map) throws Exception;
	MemberDto showMyInfo(String userid) throws SQLException;
	int rmAccount(String userid) throws SQLException;
	int updateInfo(Map<String, String> map) throws SQLException;
	List<UserBoardDto> getmyBoards(String userid)throws SQLException;
	String findSalt(String userId) throws SQLException;
	void modifyPwd(Map<String, String> map) throws SQLException;
	int checkPwd(Map<String, String> map) throws SQLException;
	void saveRefreshToken(String string, String refreshToken)throws SQLException;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
}