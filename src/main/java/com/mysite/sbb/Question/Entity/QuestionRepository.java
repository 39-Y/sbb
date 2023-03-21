package com.mysite.sbb.Question.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Optional<Question> findBySubject(String subject);

    List<Question> findBySubjectLike(String subject);

    @Modifying
    @Query(value = "alter TABLE question AUTO_INCREAMENT = 1;", nativeQuery = true)
    void clearAutoincrement();
}
