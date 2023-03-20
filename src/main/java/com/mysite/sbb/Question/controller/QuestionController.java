package com.mysite.sbb.Question.controller;

import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import com.mysite.sbb.Question.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    final QuestionService service;
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("questionList", service.list());
        return "question_list";
    }
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        model.addAttribute("question", service.detail(id));
        return "question_detail";
    }


}
