package com.mysite.sbb.Question.Service;

import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.Question.DTO.QuestionForm;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import com.mysite.sbb.user.entity.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public Page<Question> list(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> sp = search(kw);
        return repository.findAll(sp, pageable);
    }

    public Question detail(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void modify(Question question, QuestionForm questionForm) {
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setModifyDate(LocalDateTime.now());
        repository.save(question);
    }

    public void delete(Question question) {
        repository.delete(question);
    }

    public void vote(SiteUser user, Integer id) {
        Question question = repository.findById(id).orElse(null);
        if(question != null){
            question.getVoter().add(user);
            repository.save(question);
        }
    }

    public Specification<Question> search(String kw){
        return new Specification<Question>() {
            private static final long serialVersionID = 1l;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = q. join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answers", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(
                            cb.like(q.get("subject"), "%"+kw+"%"),
                            cb.like(q.get("content"), "%"+kw+"%"),
                            cb.like(u1.get("username"), "%"+kw+"%"),
                            cb.like(a.get("content"), "%"+kw+"%"),
                            cb.like(u2.get("username"), "%"+kw+"%")
                        );
            }
        };
    }
}
