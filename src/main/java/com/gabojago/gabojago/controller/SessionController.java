package com.gabojago.gabojago.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabojago.gabojago.model.dto.MemberDto;

@RestController
@RequestMapping("/session")
public class SessionController {
	
	@GetMapping("/getSession")
	public String getSession(HttpServletRequest request, @RequestParam Map<String, String> map) throws Exception {
		HttpSession session = request.getSession();
	    MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
	    String id = null;
	    if(memberDto != null) {
	    	id = memberDto.getUserId() ;
	    }
	    
		return id;
	}
	
}
