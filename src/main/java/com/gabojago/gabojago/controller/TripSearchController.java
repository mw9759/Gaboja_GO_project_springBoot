package com.gabojago.gabojago.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.TripSearchDto;
import com.gabojago.gabojago.model.service.TripSearchService;
import com.gabojago.gabojago.util.ParameterCheck;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tripsearch")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class TripSearchController {

	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private TripSearchService tripSearchService;
	
	@Autowired
	public TripSearchController(TripSearchService tripSearchService) {
		this.tripSearchService = tripSearchService;
	}
	
	@PostMapping("/list")
	@ApiOperation(value = "test", response = List.class)
	public ResponseEntity<?> list(@RequestBody Map<String, String> map) throws Exception {
		logger.debug("list map : {}", map);
		
		int sido = ParameterCheck.notNumberToZero(map.get("searchArea"));
		int contentTypeId = ParameterCheck.notNumberToOne(map.get("searchContentId"));
		String keyword = ParameterCheck.nullToBlank(map.get("searchKeyword"));
		int sortType = ParameterCheck.notNumberToOne(map.get("sortType"));
		String latitude = map.get("latitude");
		String longitude = map.get("longitude");
		if(latitude == null || latitude == "") latitude="1";
		if(longitude == null || longitude == "") longitude="1";
		
		List<TripSearchDto> result = tripSearchService.tripList(sido, contentTypeId, keyword, sortType, Double.parseDouble(latitude), Double.parseDouble(longitude));
		System.out.println(result);
		
		return new ResponseEntity<List<TripSearchDto>>(result, HttpStatus.OK);
	}
	
}