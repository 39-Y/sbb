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
//        if(page == 0)
//            for(int i=1; i<=300; i++){
//                repository.save(Question.builder()
//                        .subject(String.format("sbb가 무엇인가요?:::%3d", i))
//                        .content("sbb에 대해서 알고 싶습니다.")
//                        .createDate(LocalDateTime.now())
//                        .build());
//            }
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return repository.findAll(pageable);
    }

    public Question detail(Integer id){
        return repository.findById(id).orElse(null);
    }
}
