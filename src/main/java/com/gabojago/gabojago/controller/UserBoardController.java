package com.gabojago.gabojago.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gabojago.gabojago.model.dto.UserBoardDto;
import com.gabojago.gabojago.model.dto.MemberDto;
import com.gabojago.gabojago.model.service.UserBoardService;

@Controller
@RequestMapping("/hotpl")
public class UserBoardController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private UserBoardService userBoardService;
	
	public UserBoardController(UserBoardService userBoardService) {
		super();
		this.userBoardService = userBoardService;
	}
	
	//작성 핫플리스트 가져오기
	@GetMapping("/list")
	public String list(Model model) {
		try {
			List<UserBoardDto> list = userBoardService.listArticle();
			model.addAttribute("articles", list);
			return "hotplboard/hotplList";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "hotplboard/hotplList";
	}
	
	//핫플 게시글 작성페이지 이동
	@GetMapping("/write")
	public String mvwrite() {
		return "hotplboard/writehotpl";
	}
	
	//핫플 게시글 작성
	@PostMapping("/write")
	public String write(UserBoardDto hotplDto, Model model) {
		logger.debug("HotPlaceBoardDto info : {}", hotplDto);
		try {
			userBoardService.writeArticle(hotplDto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list(model);
	}
	
	//핫플 게시글 상세보기
	@GetMapping("/view/{articleNo}")
	public String view(@PathVariable("articleNo") int articleNo, Model model,
						HttpSession session) {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if (memberDto != null) {
			try {
				userBoardService.updateHit(articleNo);
				UserBoardDto boardDto = userBoardService.getArticle(articleNo);
				model.addAttribute("article", boardDto);

				return "hotplboard/viewHotpl";
			} catch (Exception e) {
				e.printStackTrace();
				return "index";
			}
		} else {
			model.addAttribute("msg", "로그인 후 이용해주세요");
			return "user/login_signup";
		}
	}
	
	//핫플 게시글 수정페이지 이동
	@GetMapping("/modify/{articleNo}")
	public String mvmodify(@PathVariable("articleNo") int articleNo, Model model) {
		try {
			UserBoardDto boardDto = userBoardService.getArticle(articleNo);
			model.addAttribute("article", boardDto);
			return "hotplboard/modifyHotpl";
		} catch (SQLException e) {
			e.printStackTrace();
			return "index";
		}
	}
	
	//핫플 게시글 수정
	@PostMapping("/modify")
	public String modify(UserBoardDto hotplDto, Model model, HttpSession session) {
		logger.debug("HotPlaceBoardDto info : {}", hotplDto);
		try {
			userBoardService.modifyArticle(hotplDto);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return view(hotplDto.getArticleNo(), model, session);
	}
	
	//게시글 삭제
	@GetMapping("/delete/{articleNo}")
	public String delete(@PathVariable("articleNo") int articleNo,Model model) {
		try {
			userBoardService.deleteArticle(articleNo);
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			return list(model);
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("msg", "삭제실패");
		}
		return null;
	}
}
