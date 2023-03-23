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
    public Answer save(AnswerForm answerForm, Integer id,SiteUser user) {
        return repository.save(Answer.builder()
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

    public void vote(SiteUser user, Integer answerId) {
        Answer answer = get(answerId);
        if (answer != null){
            answer.getVoter().add(user);
            repository.save(answer);
        }
    }
}
