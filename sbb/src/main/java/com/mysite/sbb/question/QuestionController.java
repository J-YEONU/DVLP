package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;





@RequestMapping("/question")
@RequiredArgsConstructor
// final이 붙은 속성을 포함하는 생성자를 자동 생성해주는 어노테이션
@Controller
public class QuestionController {
	
//	private final QuestionRepository questionRepository; 
	private final QuestionService questionService; 
	private final UserService userService;
	
	@GetMapping("/list")
//	@ResponseBody
//	아래를 문자열 그대로 내보내는 어노테이션
//	public String list(Model model) {
//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
//		return "question_list";
	
	// 페이징 구현
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question_list";
		
//	public List<Question> list() {
//		return this.questionRepository.findAll();
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questiomForm) {
		return "question_form";
	}
	
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questiomForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questiomForm.getSubject(), questiomForm.getContent(), siteUser);
		return "redirect:/question/list"; //징문 저장 후 징문 목록으로 이동
	}
	
	
	
	

}
