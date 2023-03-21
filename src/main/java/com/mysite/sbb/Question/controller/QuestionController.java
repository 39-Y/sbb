package com.mysite.sbb.Question.controller;

import com.mysite.sbb.Answer.DTO.AnswerForm;
import com.mysite.sbb.Question.DTO.QuestionForm;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import com.mysite.sbb.Question.Service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    final QuestionService service;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("paging", service.list(page));
        return "question_list";
    }
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        model.addAttribute("question", service.detail(id));
        return "question_detail";
    }
    @GetMapping("/create")
    public String showCreate(QuestionForm questionForm){
        return "question_form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult br){
        if(br.hasErrors())
            return "question_form";
        service.save(questionForm);
        return "redirect:/question/list";
    }


}
