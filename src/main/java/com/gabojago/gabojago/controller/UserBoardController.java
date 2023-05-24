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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.gabojago.gabojago.model.dto.UserBoardCommentsDto;
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
	
	//조회수 탑3 게시글만 가져오기
	@ApiOperation(value = "게시글 글목록", notes = "탑3 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/listtop3")
	public ResponseEntity<?> listTop3() { 
		try {
			return new ResponseEntity<List<UserBoardDto>>(userBoardService.listTop3(), HttpStatus.OK);
		} catch (Exception e) {
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
		System.out.println("잉?");
		for(int i = 0; i<imgs.size(); i++) {
			try {
				if(userBoardService.registImgs(imgs.get(i))) {
					
					System.out.println("등록성공");
				}
				else {
					System.out.println("등록실패");
				}
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	//해당 게시글에 이미지 수정등록
	@ApiOperation(value = "이미지 등록", response = String.class)
	@PostMapping("/updateImg")
	public ResponseEntity<?> updateImg(@RequestBody Map<String, List<String>> map
			){
		List<String> imgs = map.get("imgBlobs");
		List<String> article = map.get("articleNo");
		int articleNo = Integer.parseInt(article.get(0));
		for(int i = 0; i<imgs.size(); i++) {
			try {
				ImgInfos imgInfos = new ImgInfos(); 
				imgInfos.setArticleNo(articleNo);
				imgInfos.setImgBlob(imgs.get(i));
				
				if(userBoardService.modifyImg(imgInfos)) {
					
					System.out.println("등록성공");
				}
				else {
					System.out.println("등록실패");
				}
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	//게시글 조회수 증가
	@ApiOperation(value = "게시글 조회수 증가", notes = "글번호에 해당하는 게시글의 조회수 증가.", response = List.class)
	@GetMapping("/view/{articleNo}")
	public ResponseEntity<?> view(@PathVariable("articleNo") int articleNo,
			@ModelAttribute BoardParameterDto boardParameterDto) {
		try {
			userBoardService.updateHit(articleNo);
			return new ResponseEntity<List<UserBoardDto>>(userBoardService.listArticle(boardParameterDto), HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//등록된 이미지 삭제.deleteImg
	@ApiOperation(value = "등록된 이미지 삭제", notes = "이미지를 삭제한다.", response = String.class)
	@DeleteMapping("/deleteImg/{articleNo}")
	public ResponseEntity<?> deleteImgs(@PathVariable("articleNo") int articleNo) { 
		try {
			if(userBoardService.deleteImgs(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 수정
	@ApiOperation(value = "게시글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody UserBoardDto userBoardDto) {
		logger.debug("HotPlaceBoardDto info : {}", userBoardDto);
		try {
			if(userBoardService.modifyArticle(userBoardDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 삭제
	@ApiOperation(value = "게시글 삭제", notes = "게시글에 해당하는 게시글 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{articleNo}")
	public ResponseEntity<?> delete(@PathVariable("articleNo") int articleNo) {
		try {
			if(userBoardService.deleteArticle(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//댓글관련
	// 댓글 리스트 가져오기
	@ApiOperation(value = "댓글 리스트 가져오기", notes = "게시글에 해당하는 댓글을 가져온다.", response = List.class)
	@GetMapping("/getComments/{articleNo}")
	private ResponseEntity<?> getComments(@PathVariable("articleNo") int articleNo){
		try {
			return new ResponseEntity<List<UserBoardCommentsDto>>(userBoardService.getComments(articleNo), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	// 댓글 작성하기 writeComment
	@ApiOperation(value = "댓글 작성", notes = "새로운 댓글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/writeComment")
	public ResponseEntity<?> writeComment(@RequestBody UserBoardCommentsDto comment) {
		System.out.println(comment);
		try {
			if(userBoardService.writeComment(comment)) {
				userBoardService.updateCommentsCnt(comment.getArticleNo()); // 댓글 작성 게시글 조회수 증가.
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//댓글 삭제
	@ApiOperation(value = "댓글 삭제", notes = "게시글에 해당하는 댓글을 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@RequestBody UserBoardCommentsDto comment) {
		System.out.println("여긴오나");
		try {
			if(userBoardService.deleteComment(comment.getCommentNo())) {
				userBoardService.updateCommentsCnt(comment.getArticleNo()); // 댓글 작성 게시글 조회수 감소.
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 수정
	@ApiOperation(value = "댓글수정", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modifyComment")
	public ResponseEntity<?> modifyComment(@RequestBody UserBoardCommentsDto comment) {
		try {
			if(userBoardService.modifyComment(comment)) {
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
