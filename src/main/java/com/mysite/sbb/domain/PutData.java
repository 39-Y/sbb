package com.mysite.sbb.domain;

import com.mysite.sbb.Question.Service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PutData {
    @Bean
    public CommandLineRunner put(QuestionService service){
        return args -> {
            service.save("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
            service.save("spring 모델이 무엇인가요?", "spring은 id 자동생성 하나요.");
        };
    }
}
