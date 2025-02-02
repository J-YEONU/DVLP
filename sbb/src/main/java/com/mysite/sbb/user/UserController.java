package com.mysite.sbb.user;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final QuestionService questionService; 
	private final AnswerService answerService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		//TODO: process POST request
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		}catch(DataIntegrityViolationException e){
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/userDetail")
	public String userDetail(Principal principal, Model model,
			@RequestParam(value="qpage", defaultValue="0") int Qpage,
			@RequestParam(value="apage", defaultValue="0") int Apage) {
		//TODO: process POST request
		// 유저 이름, 이메일
		// 이름은 html에서 #authentication.getPrincipal().getUsername() 로 불러오기가 가능하지만
		// 이메일은 기본적으로 제공하는 메소드가 없음 principal 객체를 통해 이메일만 string으로 불러와서 model에 담아 전송예정
		// 유저가 쓴 게시글
		// 게시글을 page<Question>로 받아서 구현할 예정
		// 유저가 쓴 댓글 조회
		// 댓글도 마찬가지로 page<Answer>로 받아서 구현할 예정
		// 한 페이지에 두개의 게시판 형태를 표시할지 별도의 버튼을 둘지 고민
		SiteUser siteuser = userService.getUser(principal.getName());
		String userName = siteuser.getUsername();
		String userEmail = siteuser.getEmail();
		
		Page<Question> paging = this.questionService.getList(siteuser, Qpage);
		
		System.out.println(userName);
		System.out.println(userEmail);
		System.out.println(paging.toString());
		
		model.addAttribute("userName", userName);
		model.addAttribute("userEmail", userEmail);
		
		return "user_detail";
	}
	
	
}
