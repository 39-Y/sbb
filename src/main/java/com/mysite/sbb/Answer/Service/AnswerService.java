package com.mysite.sbb.Answer.Service;

import com.mysite.sbb.Answer.DTO.AnswerForm;
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
    public void save(AnswerForm answerForm, Integer id,SiteUser user) {
        repository.save(Answer.builder()
                .content(answerForm.getContent())
                .createDate(LocalDateTime.now())
                .question(questionRepository.findById(id).orElse(null))
                .author(user)
                .build());
    }
    public Answer get(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void modify(Answer answer, String content){
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        repository.save(answer);
    }

    public void delete(Integer answerId) {
        Answer answer = repository.findById(answerId).orElse(null);
        if(answer != null)
            repository.delete(answer);
    }
}
