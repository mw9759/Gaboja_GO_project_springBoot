package com.gabojago.gabojago.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.service.AdminBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/admin")
@Api("게시판 컨트롤러  API V1")
public class AdminBoardController {
	
	private final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private AdminBoardService adminBoardService;
	
	//공지사항 리스트 .
	@ApiOperation(value = "공지사항 글목록", notes = "모든 공지사항의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<AdminBoardDto>> listArticle() throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<AdminBoardDto>>(adminBoardService.listArticle(), HttpStatus.OK);
	}
	
	// 공지사항 작성 이동.
//	@GetMapping("/mvwrite")
//	public String mvwrite() {
//		return "adminboard/write";
//	}
	
	// 공지사항 작성
	@ApiOperation(value = "공지사항 글작성", notes = "새로운 공지사항 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(AdminBoardDto adminDto, Model model, HttpSession session){
		logger.debug("HotPlaceBoardDto info : {}", adminDto);
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		adminDto.setUserId(memberDto.getUserId());
		try {
			if(adminBoardService.writeArticle(adminDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 공지사항 상세보기
	@ApiOperation(value = "공지사항 상세보기", notes = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = AdminBoardDto.class)
	@GetMapping("/view/{articleNo}")
	public ResponseEntity<AdminBoardDto> getArticle(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleNo) throws Exception {
		adminBoardService.updateHit(articleNo);
		return new ResponseEntity<AdminBoardDto>(adminBoardService.getArticle(articleNo), HttpStatus.OK);
	}
	
	// 글 수정 페이지로 이동
//	@GetMapping("/mvmodify/{articleNo}")
//	public String mvmodify(@PathVariable("articleNo") int articleNo, Model model) {
//		try {
//			AdminBoardDto boardDto = adminBoardService.getArticle(articleNo);
//			model.addAttribute("article", boardDto);
//			return "adminboard/modify";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "index";
//		}
//	}
	
	//공지사항 수정
	@ApiOperation(value = "공지사항수정", notes = "수정할 공지사항 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 공지사항정보.", required = true)AdminBoardDto adminDto) {
		logger.debug("HotPlaceBoardDto info : {}", adminDto);
		
		try {
			if(adminBoardService.modifyArticle(adminDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 삭제
	@ApiOperation(value = "공지사항 삭제", notes = "공지사항번호에 해당하는 공지사항 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/delete/{articleNo}")
	public ResponseEntity<String> delete(@PathVariable("articleNo") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleNo) {
		try {
			if(adminBoardService.deleteArticle(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//에러처리
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
