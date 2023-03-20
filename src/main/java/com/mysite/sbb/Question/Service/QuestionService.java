package com.mysite.sbb.Question.Service;

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

    public void save(String subject, String content){
        repository.save(Question.builder()
                .subject(subject)
                .content(content)
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
