package com.mysite.sbb.Answer.controller;

import com.mysite.sbb.Answer.DTO.AnswerForm;
import com.mysite.sbb.Answer.Service.AnswerService;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Service.QuestionService;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    final AnswerService service;
    final UserService userService;
    final QuestionService questionService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               Principal principal,
                               @PathVariable Integer id,
                               @Valid AnswerForm answerForm,
                               BindingResult bindingResult){
        Question question = questionService.detail(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }

        SiteUser user = userService.getUser(principal.getName());
        service.save(answerForm.getContent(), id, user);
        return String.format("redirect:/question/detail/%s", id);
    }
}
