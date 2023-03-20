package com.mysite.sbb.Question.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Optional<Question> findBySubject(String subject);

    List<Question> findBySubjectLike(String subject);
}
