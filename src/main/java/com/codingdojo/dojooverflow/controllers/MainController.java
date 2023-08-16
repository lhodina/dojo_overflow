package com.codingdojo.dojooverflow.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.dojooverflow.models.Answer;
import com.codingdojo.dojooverflow.models.Question;
import com.codingdojo.dojooverflow.models.Tag;
import com.codingdojo.dojooverflow.services.AnswerService;
import com.codingdojo.dojooverflow.services.QuestionService;
import com.codingdojo.dojooverflow.services.TagService;

import jakarta.validation.Valid;

@Controller
public class MainController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<Question> allQuestions = questionService.allQuestions();
		model.addAttribute("allQuestions", allQuestions);
		return "index.jsp";
	}
	
	@GetMapping("/questions/new")
	public String newQuestionForm(@ModelAttribute("question") Question question) {
		return "newQuestionForm.jsp";
	}
	
	@PostMapping("/questions")
	public String askQuestion(
			@RequestParam(value="text") String text,
			@RequestParam(value="tags") String tags
			) { 
		Question question = new Question();
		question.setText(text);
		
		List<String> tagsSplit = Arrays.asList(tags.split(",", -1));
		ArrayList<Tag> questionTags = new ArrayList<Tag>();
		
		for (String tagString : tagsSplit) {
			Tag newTag = new Tag();
			newTag.setSubject(tagString);
			Tag currentTag = tagService.createTag(newTag);
			questionTags.add(currentTag);
		}
		
		question.setTags(questionTags);
		questionService.createQuestion(question);
		return "redirect:/";
	}
	
	@GetMapping("/questions/{questionId}")
	public String showQuestion(
			@ModelAttribute("answer") Answer answer,
			@PathVariable("questionId") Long questionId, 
			Model model
		) {
		Question question = questionService.findQuestion(questionId);
		model.addAttribute("question", question);
		return "showQuestion.jsp";
	}
	
	@PostMapping("/questions/{questionId}/answers")
	public String answerQuestion(
		@Valid @ModelAttribute("answer") Answer answer,
		BindingResult result,
		@PathVariable("questionId") Long questionId
		) {
		
		if (result.hasErrors()) {
			return "showQuestion.jsp";
		} else {
			Question question = questionService.findQuestion(questionId);
			answer.setQuestion(question);
			Answer thisAnswer = answerService.createAnswer(answer);
			question.getAnswers().add(thisAnswer);
			questionService.updateQuestion(question);
			
			return "redirect:/questions/{questionId}";
		}
		
	}
	
}
