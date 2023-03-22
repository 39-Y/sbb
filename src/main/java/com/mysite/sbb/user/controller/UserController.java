package com.mysite.sbb.user.controller;

import com.mysite.sbb.user.DTO.UserCreateForm;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult br){
        if(br.hasErrors())
            return "signup_form";
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            br.rejectValue("password2",
                    "passwordInCorrect",
                    "비밀번호와 확인이 동일하지 않습니다.");
            return "signup_form";
        }
        try{
            service.create(userCreateForm);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            br.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch (Exception e){
            e.printStackTrace();
            br.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login_form";
    }
}
