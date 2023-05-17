package com.gabojago.gabojago.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public boolean joinMember(MemberDto memberDto) throws Exception {
		return memberMapper.joinMember(memberDto);
	}
	
	@Override
	public String findId(Map<String, String> map) throws Exception {
		return memberMapper.findId(map);
	}

	@Override
	public MemberDto loginMember(Map<String, String> map) throws Exception {
		return memberMapper.loginMember(map);
	}

	@Override
	public String findPwd(Map<String, String> map)
			throws Exception {
		return memberMapper.findPwd(map);
	}

	@Override
	public MemberDto showMyInfo(String userid) throws SQLException {
		
		return memberMapper.showMyInfo(userid);
	}

	@Override
	public int rmAccount(String userid) throws SQLException {
		return memberMapper.rmAccount(userid);
	}

	@Override
	public int updateInfo(Map<String, String> map) throws SQLException {
		return memberMapper.updateInfo(map);
	}

	@Override
	public String findSalt(String userId) throws SQLException {
		
		return memberMapper.findSalt(userId);
	}

	@Override
	public void modifyPwd(Map<String, String> map) throws SQLException {
		memberMapper.modifyPwd(map);
	}

	@Override
	public int checkPwd(Map<String, String> map) throws SQLException {
		return memberMapper.checkPwd(map);
	}

	@Override
	public List<UserBoardDto> getmyBoards(String userid) throws SQLException {
		return memberMapper.getmyBoards(userid);
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws SQLException{
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
//		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}
	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return memberMapper.getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
}