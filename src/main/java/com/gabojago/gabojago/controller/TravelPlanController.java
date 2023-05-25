package com.gabojago.gabojago.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gabojago.gabojago.model.dto.AttractionCommentDto;
import com.gabojago.gabojago.model.dto.TravelPlanDto;
import com.gabojago.gabojago.model.service.TravelPlanService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/travelplan")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class TravelPlanController {

	private TravelPlanService travelPlanService;
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	public TravelPlanController(TravelPlanService travelPlanService) {
		this.travelPlanService = travelPlanService;
	}
	
	// 여행 계획 조회
	@ApiOperation(value = "여행 계획 조회", notes = "사용자가 작성한 여행 계획 목록을 조회한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam String userId) throws Exception {
		List<String> result = travelPlanService.planList(userId);
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	// 여행 계획 생성
	@ApiOperation(value = "여행 계획 생성", notes = "여행 계획을 생성한다.",  response = Boolean.class)
	@PostMapping("/create")
	public ResponseEntity<Boolean> createPlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.createPlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	// 여행 계획 제거
	@ApiOperation(value = "여행 계획 제거", notes = "여행 계획을 제거한다.", response = Boolean.class)
	@DeleteMapping("/remove")
	public ResponseEntity<Boolean> removePlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.removePlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	// 여행 계획 수정
	@ApiOperation(value = "여행 계획 수정", notes = "수정된 여행 계획을 저장한다.", response = Boolean.class)
	@PostMapping("/save")
	public ResponseEntity<Boolean> savePlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.savePlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	// 여행 계획 상세조회
	@ApiOperation(value = "여행 계획 상세조회", notes = "여행 계획의 상세내용을 조회한다.", response = String.class)
	@GetMapping("/load")
	public ResponseEntity<String> loadPlan(@ModelAttribute TravelPlanDto travelPlanDto) throws Exception {
		String result = travelPlanService.loadPlan(travelPlanDto);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	// 댓글 조회
	@ApiOperation(value = "댓글 조회", notes = "관광지에 대한 댓글을 조회한다.", response = List.class)
	@GetMapping("/getComments/{contentId}")
	private ResponseEntity<?> getComments(@PathVariable("contentId") int contentId){
		try {
			return new ResponseEntity<List<AttractionCommentDto>>(travelPlanService.getComments(contentId), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 작성
	@ApiOperation(value = "댓글 작성", notes = "관광지에 대한 댓글을 작성한다.", response = String.class)
	@PostMapping("/writeComment")
	public ResponseEntity<?> writeComment(@RequestBody AttractionCommentDto comment) {
		try {
			if(travelPlanService.writeComment(comment)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//댓글 삭제
	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.", response = String.class)
	@PostMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@PathVariable("contentId") int commentId) {
		try {
			if(travelPlanService.deleteComment(commentId)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 수정
	@ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.", response = String.class)
	@PutMapping("/modifyComment")
	public ResponseEntity<?> modifyComment(@RequestBody AttractionCommentDto comment) {
		try {
			if(travelPlanService.modifyComment(comment)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 관광지 좋아요 정보 업데이트
	@PutMapping(value="/updateLike")
	@ApiOperation(value = "관광지 좋아요 정보 업데이트", notes = "관광지 좋아요 정보를 업데이트한다.", response = String.class)
	public ResponseEntity<String> updateLike(@RequestBody Map<String, String> map){
		try {
			if(travelPlanService.updateLike(map)) {
				travelPlanService.myLike(map);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			else return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//에러처리
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}