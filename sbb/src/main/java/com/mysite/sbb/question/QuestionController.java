package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;



@RequestMapping("/question")
@RequiredArgsConstructor
// final이 붙은 속성을 포함하는 생성자를 자동 생성해주는 어노테이션
@Controller
public class QuestionController {
	
//	private final QuestionRepository questionRepository; 
	private final QuestionService questionService; 
	
	@GetMapping("/list")
//	@ResponseBody
//	아래를 문자열 그대로 내보내는 어노테이션
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
//	public List<Question> list() {
//		return this.questionRepository.findAll();
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	

}
