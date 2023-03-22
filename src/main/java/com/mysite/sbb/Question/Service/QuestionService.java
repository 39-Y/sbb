package com.mysite.sbb.Question.Service;

import com.mysite.sbb.Question.DTO.QuestionForm;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import com.mysite.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class QuestionService {
    final QuestionRepository repository;

    public void save(QuestionForm qf, SiteUser user){
        repository.save(Question.builder()
                .subject(qf.getSubject())
                .content(qf.getContent())
                .createDate(LocalDateTime.now())
                .author(user)
                .build());
    }
    public Page<Question> list(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return repository.findAll(pageable);
    }

    public Question detail(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void modify(Question question, QuestionForm questionForm) {
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setModifiyTime(LocalDateTime.now());
        repository.save(question);
    }
}
