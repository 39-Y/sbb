package com.mysite.sbb.Question.Service;

import com.mysite.sbb.Question.DTO.QuestionForm;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Service
public class QuestionService {
    final QuestionRepository repository;

    public void save(QuestionForm qf){
        repository.save(Question.builder()
                .subject(qf.getSubject())
                .content(qf.getContent())
                .createDate(LocalDateTime.now())
                .build());
    }
    public List<Question> list(){
        return repository.findAll();
    }

    public Question detail(Integer id){
        return repository.findById(id).orElse(null);
    }
}
