package com.gabojago.gabojago.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.TravelPlanDto;
import com.gabojago.gabojago.model.service.TravelPlanService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/travelplan")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class TravelPlanController {

	private TravelPlanService travelPlanService;
	
	@Autowired
	public TravelPlanController(TravelPlanService travelPlanService) {
		this.travelPlanService = travelPlanService;
	}
	
	@ResponseBody
	@GetMapping("/list")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<?> list(@RequestParam String userId) throws Exception {
		List<String> result = travelPlanService.planList(userId);
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping("/create")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<Boolean> createPlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.createPlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	@ResponseBody
	@DeleteMapping("/remove")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<Boolean> removePlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.removePlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	@ResponseBody
	@PostMapping("/save")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<Boolean> savePlan(@RequestBody TravelPlanDto travelPlanDto) throws Exception {
		try {
			travelPlanService.savePlan(travelPlanDto);
		} catch(Exception e) {
			return ResponseEntity.ok(false);
		}
		
		return ResponseEntity.ok(true);
	}
	
	@ResponseBody
	@GetMapping("/load")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<String> loadPlan(@ModelAttribute TravelPlanDto travelPlanDto) throws Exception {
		System.out.println("!!!!!!!" + travelPlanDto);
		String result = travelPlanService.loadPlan(travelPlanDto);
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
}