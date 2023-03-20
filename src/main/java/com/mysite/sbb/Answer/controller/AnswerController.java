package com.mysite.sbb.Answer.controller;

import com.mysite.sbb.Answer.Service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    final AnswerService service;
    @PostMapping("/create/{id}")
    public String createAnswer(String content, @PathVariable Integer id){
        service.save(content, id);
        return String.format("redirect:/question/detail/%s", id);
    }
}
