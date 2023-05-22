package com.gabojago.gabojago.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.AdminBoardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.dto.ImgInfos;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.service.UserBoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/userboard")
public class UserBoardController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private UserBoardService userBoardService;
	
	
	//작성 게시글 가져오기
	@ApiOperation(value = "게시글 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<?> list(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) @ModelAttribute BoardParameterDto boardParameterDto) { 
		try {
			return new ResponseEntity<List<UserBoardDto>>(userBoardService.listArticle(boardParameterDto), HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//등록 이미지 가져오기
	@ApiOperation(value = "등록된 이미지 목록", notes = "모든 이미지를 반환한다.", response = List.class)
	@GetMapping("/getImgs")
	public ResponseEntity<?> getImgs(@ModelAttribute ImgInfos imgs) { 
		try {
			return new ResponseEntity<List<ImgInfos>>(userBoardService.getImgs(imgs), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 페이징 처리
		@ApiOperation(value = "게시판 총개수", notes = "게시판의 총 개수를 반환한다.", response = Integer.class)
		@GetMapping("/total")
		public ResponseEntity<Integer> getNum(@RequestParam Map<String,String> map) throws Exception {
			logger.info("getNum 호출");
			return new ResponseEntity<Integer>(userBoardService.getNum(map), HttpStatus.OK);
		}
	
	//게시글 작성
	@ApiOperation(value = "게시글 작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody UserBoardDto userBoardDto) {
		logger.debug("HotPlaceBoardDto info : {}", userBoardDto);
		System.out.println(userBoardDto);
		try {
			if(userBoardService.writeArticle(userBoardDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글에 올라온 이미지 등록
	@ApiOperation(value = "이미지 등록", response = String.class)
	@PostMapping("/upload")
	public ResponseEntity<?> setImg(@RequestBody Map<String, List<String>> map){
		List<String> imgs = map.get("imgBlobs");
		
		for(int i = 0; i<imgs.size(); i++) {
			try {
				if(userBoardService.registImgs(imgs.get(i))) {
					
					System.out.println("등록성공");
				}
				else {
					System.out.println("등록실1");
				}
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		
//		try {
//			if(userBoardService.registImgs(imgs)) {
//				System.out.println("등록성공");
//				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//			}
//			System.out.println("등록할게 없음");
//			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			System.out.println("등록실패");
//			return exceptionHandling(e);
//		}
	}
	
	//게시글 상세보기
	@ApiOperation(value = "게시글 상세보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = UserBoardDto.class)
	@GetMapping("/view/{articleNo}")
	public ResponseEntity<?> view(@PathVariable("articleNo") int articleNo) {
		try {
			userBoardService.updateHit(articleNo);
			return new ResponseEntity<UserBoardDto>(userBoardService.getArticle(articleNo), HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 수정페이지 이동
//	@GetMapping("/modify/{articleNo}")
//	public String mvmodify(@PathVariable("articleNo") int articleNo, Model model) {
//		try {
//			UserBoardDto boardDto = userBoardService.getArticle(articleNo);
//			model.addAttribute("article", boardDto);
//			return "hotplboard/modifyHotpl";
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "index";
//		}
//	}
	
	//게시글 수정
	@ApiOperation(value = "게시글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody UserBoardDto hotplDto) {
		logger.debug("HotPlaceBoardDto info : {}", hotplDto);
		try {
			if(userBoardService.modifyArticle(hotplDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 삭제
	@ApiOperation(value = "게시글 삭제", notes = "게시글에 해당하는 게시글 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/delete/{articleNo}")
	public ResponseEntity<?> delete(@PathVariable("articleNo") int articleNo,Model model) {
		try {
			if(userBoardService.deleteArticle(articleNo)) {
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
