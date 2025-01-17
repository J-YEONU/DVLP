package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
//	private QuestionRepository questionRepository;
	private QuestionService questionService;
	
	@Test
	void testJpa() {
		/*
		 * Question q1 = new Question(); q1.setSubject("sbb가 무엇인가요?");
		 * q1.setContent("sbb에 대해서 알고 싶습니다."); q1.setCreateDate(LocalDateTime.now());
		 * this.questionRepository.save(q1);
		 * 
		 * Question q2 = new Question(); q2.setSubject("스프링 부트 모델 질문입니다");
		 * q2.setContent("id는 자동으로 생성되나요?"); q2.setCreateDate(LocalDateTime.now());
		 * this.questionRepository.save(q2);
		 */
		for (int i = 1; i <=300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용 없음";
			this.questionService.create(subject, content);
			
		}
	
	}
	
	

}
