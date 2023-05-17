package com.gabojago.gabojago.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sequrity")
public class SequrityController {
	
	@Autowired
	private SequrityService sequrityService;
	
	@GetMapping("/create/token")
	@ApiOperation(value = "토큰 만들기", notes = "만들어진 토큰 반환", response = Map.class)
	public Map<String, Object> createToken(@RequestParam(value = "subject") String subject){
		String token = sequrityService.createToken(subject, (2*1000*60));
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("result", token);
		return map;
	}
	
	@GetMapping("/get/subject")
	@ApiOperation(value = "토큰 복호화", notes = "토큰 복호화", response = Map.class)
	public Map<String, Object> getSubject(@RequestParam(value = "token") String token){
		String subject = sequrityService.getSubject(token);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("result", subject);
		return map;
	}
	
}
