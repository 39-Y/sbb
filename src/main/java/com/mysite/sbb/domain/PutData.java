package com.mysite.sbb.domain;

import com.mysite.sbb.Question.DTO.QuestionForm;
import com.mysite.sbb.Question.Service.QuestionService;
import com.mysite.sbb.user.DTO.UserCreateForm;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PutData {
    @Bean
    public CommandLineRunner put(QuestionService service, UserService userService){
        return args -> {
            SiteUser user= userService.create(
                    new UserCreateForm("test", "test", "test", "test"));
            service.save(new QuestionForm("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다."), user);
            service.save(new QuestionForm("spring 모델이 무엇인가요?", "spring은 id 자동생성 하나요."), user);

        };
    }
}
