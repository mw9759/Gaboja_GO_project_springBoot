package com.gabojago.gabojago.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.gabojago.gabojago.model.dto.QnAboardDto;
import com.gabojago.gabojago.model.dto.BoardParameterDto;
import com.gabojago.gabojago.model.service.QnaBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
@Api("게시판 컨트롤러  API V1")
public class QnABoardController {
	
	private final Logger logger = LoggerFactory.getLogger(QnABoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private QnaBoardService qnaBoardService;
	
	// qna 목록
	@ApiOperation(value = "qna 목록", notes = "모든 qna의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<QnAboardDto>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) @ModelAttribute BoardParameterDto boardParameterDto) throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<QnAboardDto>>(qnaBoardService.listArticle(boardParameterDto), HttpStatus.OK);
	}
	
	// qnaresult 목록
	@ApiOperation(value = "qnarult 목록", notes = "모든 qnaresult의 정보를 반환한다.", response = List.class)
	@GetMapping("/listr")
	public ResponseEntity<List<QnAboardDto>> listArticler(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) @ModelAttribute BoardParameterDto boardParameterDto) throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<QnAboardDto>>(qnaBoardService.listArticler(boardParameterDto), HttpStatus.OK);
	} 
	
	// qna 작성
	@ApiOperation(value = "qna 작성", notes = "새로운 qna 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody QnAboardDto adminDto){
		System.out.println(adminDto.toString());
		logger.debug("HotPlaceBoardDto info : {}", adminDto);
		try {
			if(qnaBoardService.writeArticle(adminDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	//qnaresult 작성
	@ApiOperation(value = "qnaresult 작성", notes = "새로운 qnaresult 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/writeresult")
	public ResponseEntity<String> writer(){
		try {
			if(qnaBoardService.writeArticler()) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 총개수", notes = "게시판의 총 개수를 반환한다..", response = Integer.class)
	@GetMapping("/total")
	public ResponseEntity<Integer> getNum(@RequestParam Map<String,String> map) throws Exception {
		logger.info("getNum 호출");
		return new ResponseEntity<Integer>(qnaBoardService.getNum(map), HttpStatus.OK);
	}
	
	// qna 상세보기
	@ApiOperation(value = "qna 상세보기", notes = "글번호에 해당하는 qna의 정보를 반환한다.", response = QnAboardDto.class)
	@GetMapping("/view/{articleNo}")
	public ResponseEntity<QnAboardDto> getArticle(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleNo) throws Exception {
		return new ResponseEntity<QnAboardDto>(qnaBoardService.getArticle(articleNo), HttpStatus.OK);
	}
	
	//qna 수정
	@ApiOperation(value = "qna수정", notes = "수정할 qna 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 qna정보.", required = true)QnAboardDto adminDto) {
		logger.debug("HotPlaceBoardDto info : {}", adminDto);
		
		try {
			if(qnaBoardService.modifyArticle(adminDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//qnaResult 수정
	@ApiOperation(value = "qnaResult수정", notes = "수정할 qnaResult 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modifyresult")
	public ResponseEntity<String> modifyr(@RequestBody @ApiParam(value = "수정할 qnaResult정보.", required = true)QnAboardDto adminDto) {
		try {
			if(qnaBoardService.modifyArticler(adminDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// isAnswered업데이트 updateIsAnswered
	@ApiOperation(value = "isAnswered수정", notes = "수정할 isAnswered 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/updateIsAnswered/{articleNo}")
	public ResponseEntity<String> updateAnswer(@PathVariable("articleNo") @ApiParam(value = "수정할 글의 글번호.", required = true) int articleNo) {
		try {
			if(qnaBoardService.updateIsAnswered(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@ApiOperation(value = "isAnswered수정", notes = "수정할 isAnswered 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/updateNoAnswered/{articleNo}")
	public ResponseEntity<String> updateNoAnswer(@PathVariable("articleNo") @ApiParam(value = "수정할 글의 글번호.", required = true) int articleNo) {
		try {
			if(qnaBoardService.updateNoAnswered(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//게시글 삭제
	@ApiOperation(value = "qna 삭제", notes = "qna번호에 해당하는 qna 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{articleNo}")
	public ResponseEntity<String> delete(@PathVariable("articleNo") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleNo) {
		try {
			if(qnaBoardService.deleteArticle(articleNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//qnaresult 삭제
	@ApiOperation(value = "qnares 삭제", notes = "qnares번호에 해당하는 qnares 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/deleteresult/{articleNo}")
	public ResponseEntity<String> deleter(@PathVariable("articleNo") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleNo) {
		try {
			if(qnaBoardService.deleteArticler(articleNo)) {
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
