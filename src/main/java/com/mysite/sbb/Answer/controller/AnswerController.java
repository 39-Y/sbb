package com.mysite.sbb.Answer.controller;

import com.mysite.sbb.Answer.DTO.AnswerForm;
import com.mysite.sbb.Answer.Service.AnswerService;
import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Service.QuestionService;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Answer answer = service.save(answerForm, id, user);
        return String.format("redirect:/question/detail/%s#answer_%s"
                , answer.getQuestion().getId()
                , answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(Principal principal,
                               @PathVariable(value = "id") Integer answerId,
                               AnswerForm answerForm){
        Answer answer = service.get(answerId);
        if(answer==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 댓글 입력입니다.");
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(Principal principal,
                               @PathVariable(value = "id") Integer answerId,
                               @Valid AnswerForm answerForm,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "question_form";
        Answer answer = service.get(answerId);
        if(!answer.getAuthor().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 권한이 없습니다.");
        service.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s"
                , answer.getQuestion().getId()
                , answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal,
                               @PathVariable(value = "id") Integer answerId){
        Answer answer = service.get(answerId);
        if(!answer.getAuthor().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 삭제 권한이 없습니다.");
        service.delete(answerId);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal,
                               @PathVariable Integer answerId){
        int questionId = service.get(answerId).getQuestion().getId();
        service.vote(userService.getUser(principal.getName()), answerId);
        return String.format("redirect:/question/detail/%s#%s", questionId, answerId);
    }
}
