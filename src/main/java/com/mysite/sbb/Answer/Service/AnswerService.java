package com.mysite.sbb.Answer.Service;

import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.Answer.entity.AnswerRepository;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import com.mysite.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    final AnswerRepository repository;
    final QuestionRepository questionRepository;
    public void save(String content, Integer id, SiteUser user) {
        repository.save(Answer.builder()
                .content(content)
                .createTime(LocalDateTime.now())
                .question(questionRepository.findById(id).orElse(null))
                .author(user)
                .build());
    }
}
