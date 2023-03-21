package com.mysite.sbb.Question.Entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Optional<Question> findBySubject(String subject);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE question SET id = 1;", nativeQuery = true)
    void clearAutoIncrement();


}
