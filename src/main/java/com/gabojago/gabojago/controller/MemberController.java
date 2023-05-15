package com.gabojago.gabojago.controller;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.service.MemberService;

import io.swagger.annotations.ApiOperation;


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

	
    // 로그인 페이지 이동.
//	@GetMapping("/login")
//	public String mvlogin() {
//		return "user/login_signup";
//	}
	
	// 로그인
	@PostMapping("/login")
	@ApiOperation(value = "{id}에 해당하는 사용자 정보를 반환한다.", response = MemberDto.class)
	public ResponseEntity<?> login(
						@RequestBody Map<String, String> map,
						HttpSession session, HttpServletResponse response,
						HttpServletRequest request) {
		logger.debug("login map : {}", map);
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
		
		// 입력 계정이 있는지 확인.
		try {
			MemberDto memberDto = memberService.loginMember(map);
			
			//세션에 유저 정보 저장.
			session.setAttribute("userinfo", memberDto);
			// 쿠키에 아이디 정보 저장.=> 로그인시 아이디 저장.
			Cookie cookie = new Cookie("userid", map.get("userid"));
			cookie.setPath(request.getContextPath());
			if("ok".equals(map.get("saveid"))) {
				cookie.setMaxAge(60*60*24*365*40);
			} else {
				cookie.setMaxAge(0);
			}
			response.addCookie(cookie);
			if(memberDto != null) {
				System.out.println("login success");
				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			}
			System.out.println("login fail");
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	// 로그아웃.
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
	
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
			
			// 전화번호 dto에 담기.
			String fnum = map.get("fnum");
			String bnum = map.get("bnum");
			memberDto.setUserPhonNum("010"+fnum+bnum);
			
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
	@GetMapping("/mywrites")
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
	@PostMapping(value = "/checkpwd")
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
	@DeleteMapping("/delete/{userId}")
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
	@PutMapping(value = "/modifyMyInfo")
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