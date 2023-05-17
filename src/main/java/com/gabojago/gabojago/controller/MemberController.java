package com.gabojago.gabojago.controller;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.service.JwtServiceImpl;
import com.gabojago.gabojago.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class MemberController{
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtServiceImpl jwtService;
    // 로그인 페이지 이동.
//	@GetMapping("/login")
//	public String mvlogin() {
//		return "user/login_signup";
//	}
	
	// 로그인
	@PostMapping("/login")
	@ApiOperation(value = "{id}에 해당하는 사용자 정보를 반환한다.", response = Map.class)
	public ResponseEntity<?> login(
						@RequestBody Map<String, String> map,
						HttpSession session, HttpServletResponse response,
						HttpServletRequest request) {
		// 입력 아이디가 있다면 해당 계정의 salt값 가져오기.
		String userPwd = "";
		try {
			String SALT = memberService.findSalt(map.get("userid"));
			userPwd = Hashing(map.get("userpwd").getBytes(), SALT);
			map.remove("userpwd");
			map.put("userpwd", userPwd);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
		
		// 토큰 생성 준비
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		// 입력 계정이 있는지 확인.
		try {
			MemberDto memberDto = memberService.loginMember(map);
			if(memberDto != null) {
				System.out.println("login success");
				String accessToken = jwtService.createAccessToken("userid", map.get("userid"));// key, data
				String refreshToken = jwtService.createRefreshToken("userid", map.get("userid"));// key, data
				memberService.saveRefreshToken(map.get("userid"), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
//				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			}else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
				System.out.println("login fail");
			}
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
			
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	// access토큰 유효성 검사
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.showMyInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 로그아웃.
//	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	//토큰 재발급
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	//아이디 중복확인
	@GetMapping("/{userid}")
	@ApiOperation(value = "아이디 중복확인.", response = Integer.class)
	public ResponseEntity<?> idCheck(@PathVariable("userid") String userId) {
		logger.debug("idCheck userid : {}", userId);
		try {
			int cnt = memberService.idCheck(userId);
			return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//회원가입
	@PostMapping("/join")
	@ApiOperation(value = "회원가입.", response = String.class)
	public ResponseEntity<?> join(@RequestBody Map<String, String> map) {
		MemberDto memberDto = new MemberDto();
		System.out.println(map);
		try {
			//입력값 dto에 담기.
			memberDto.setUserName(map.get("username"));
			memberDto.setUserId(map.get("userid"));
			memberDto.setEmailId(map.get("emailid"));
			memberDto.setEmailDomain(map.get("emaildomain"));
			memberDto.setUserPhonNum(map.get("fullNum"));
			
			// 비밀번호 암호화----------------------------------------
			String SALT = getSALT(); // 개인 SALT생성
			String bfPwd = map.get("userpwd");
			String afPwd = Hashing(bfPwd.getBytes(), SALT); // 비밀번호 해싱! SALT사용
			memberDto.setUserPwd(afPwd);
			memberDto.setSalt(SALT);
			if(memberService.joinMember(memberDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK); 
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	//아이디 찾기
	@PostMapping(value = "/findid")
	@ApiOperation(value = "사용자 아이디 찾기.", response = String.class)
	public ResponseEntity<?> findId(@RequestBody Map<String, String> map) {
		logger.debug("userRegister memberDto : {}", map);
		try {
			String id = memberService.findId(map);
			return new ResponseEntity<String>(id, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//비밀번호 찾기
	@PostMapping(value = "/findpw")
	@ApiOperation(value = "사용자 비빌번호 찾기.", response = String.class)
	public ResponseEntity<?> findPw(@RequestBody Map<String, String> map) {
		logger.debug("userRegister memberDto : {}", map);
		try {
			String id = memberService.findPwd(map);
			return new ResponseEntity<String>(id, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//비밀번호 재설정/변경
	@PutMapping(value="/modifypw")
	@ApiOperation(value = "사용자 비빌번호 재설정.", response = String.class)
	public ResponseEntity<?> modifyPw(@RequestBody Map<String, String> map){
		logger.debug("userRegister memberDto : {}", map);
		try {
			// 비밀번호 암호화----------------------------------------
			String SALT = getSALT(); // 개인 SALT생성
			String bfPwd = map.get("userPwd");
			String afPwd = Hashing(bfPwd.getBytes(), SALT); // 비밀번호 해싱! SALT사용
			map.remove("userPwd");
			map.put("userPwd", afPwd);
			map.put("salt", SALT);
			memberService.modifyPwd(map);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//마이페이지 : 내정보 출력
	@GetMapping("/myinfo")
	@ApiOperation(value = "내 정보 가져오기", response = MemberDto.class)
	public ResponseEntity<?> mvMyPage(HttpSession session, 
						   HttpServletResponse response
						   ) {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if (memberDto != null) {
			String userid = memberDto.getUserId();
			MemberDto myInfo = null;
			try {
				myInfo = memberService.showMyInfo(userid);
				if(myInfo != null) {
					return new ResponseEntity<MemberDto>(myInfo, HttpStatus.OK);
				}
				else
					return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				
//				List<HotPlaceBoardDto> mylist = memberService.getmyHotPlace(userid);
//				if(myInfo != null) {
//					model.addAttribute("myInfo", myInfo);
//					model.addAttribute("myhotpls", mylist);
//					String fnum = myInfo.getUserPhonNum().substring(3, 7);
//					String bnum = myInfo.getUserPhonNum().substring(7);
//					model.addAttribute("fnum", fnum);
//					model.addAttribute("bnum", bnum);
//					return "user/mypage";
//				}
			} catch (SQLException e) { //에러처리
				return exceptionHandling(e);
			}
		}
		else { // 로그인 정보가 없음.
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	//마이페이지 : 내가 쓴 글 출력
	@GetMapping("/mypage/mywrites")
	@ApiOperation(value = "내 가 쓴 글 가져오기", response = List.class)
	public ResponseEntity<?> getMyWrites(HttpSession session){
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		try {
			return new ResponseEntity<List<UserBoardDto>>(memberService.getmyBoards(memberDto.getUserId()), HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//비밀번호 확인
	@PostMapping(value = "/mypage/checkpwd")
	@ApiOperation(value = "비밀번호 확인", response = Integer.class)
	public ResponseEntity<?> checkPwd(@RequestBody Map<String, String> map) {
		logger.debug("idCheck userid : {}", map);
		int cnt;
		// 입력 아이디가 있다면 해당 계정의 salt값 가져오기.
		String userPwd = "";
		try {
			String SALT = memberService.findSalt(map.get("userId"));
			userPwd = Hashing(map.get("userPwd").getBytes(), SALT);
			map.remove("userPwd");
			map.put("userPwd", userPwd);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return exceptionHandling(e);
		}
		
		try {
			cnt = memberService.checkPwd(map);
			return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//회원탈퇴
	@DeleteMapping("/mypage/delete/{userId}")
	@ApiOperation(value = "{id} 에 해당하는 사용자 정보를 삭제한다.", response = String.class)
	public ResponseEntity<?> delete(@PathVariable("userId") String userId,
							HttpSession session) {
		session.invalidate();
		try {
			memberService.rmAccount(userId);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
		
	}
	
	//개인정보 변경
	@PutMapping(value = "/mypage/modifyMyInfo")
	@ApiOperation(value = "사용자 정보를 수정한다.", response = String.class)
	public ResponseEntity<?> modifyMyInfo(@RequestBody Map<String, String> map) {
		// 비밀번호 암호화----------------------------------------
		try {
			String SALT = getSALT(); // 개인 SALT생성
			String bfPwd = map.get("userPwd");
			String afPwd = Hashing(bfPwd.getBytes(), SALT); // 비밀번호 해싱! SALT사용
			map.remove("userPwd");
			map.put("userPwd", afPwd);
			map.put("salt", SALT);
		} catch (Exception e) {
			return exceptionHandling(e);
		} 
		
		// 정보 업데이트
		int signal = 0;
		try {
			signal = memberService.updateInfo(map);
			if(signal != 0) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
// -----------------------비밀번호 암호화 관련 메서드--------------------------------------------------------------------------------
	// 비밀번호 암호화 개인 salt값 생성
	private static final int SALT_SIZE = 16;
	private String getSALT() throws Exception {
		SecureRandom rnd = new SecureRandom();
		byte[] temp = new byte[SALT_SIZE];
		rnd.nextBytes(temp);
		
		return Byte_to_String(temp);
		
	}
	// 비밀번호 해싱  
	private String Hashing(byte[] password, String Salt) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");	// SHA-256 해시함수를 사용 
	 
		// key-stretching
		for(int i = 0; i < 10000; i++) {
			String temp = Byte_to_String(password) + Salt;	// 패스워드와 Salt 를 합쳐 새로운 문자열 생성 
			md.update(temp.getBytes());						// temp 의 문자열을 해싱하여 md 에 저장해둔다 
			password = md.digest();							// md 객체의 다이제스트를 얻어 password 를 갱신한다 
		}
		
		return Byte_to_String(password);
	}
	// 바이트 값을 16진수로 변경해준다 
	private String Byte_to_String(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for(byte a : temp) {
			sb.append(String.format("%02x", a));
		}
		return sb.toString();
	}
	
	//에러처리
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
//-----------------------------------------------------------------------------------------------------