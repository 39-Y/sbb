package com.mysite.sbb;

import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.Answer.entity.AnswerRepository;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    QuestionRepository repository;
    @Autowired
    AnswerRepository answerRepository;

    @BeforeEach
    void before(){
        repository.deleteAll();
        repository.clearAutoIncrement();
        //저장하기
        repository.save(Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .createDate(LocalDateTime.now())
                .build());

        repository.save(Question.builder()
                .subject("spring 모델이 무엇인가요?")
                .content("spring은 id 자동생성 하나요.")
                .createDate(LocalDateTime.now())
                .build());
    }

    @Test
    @DisplayName("질문 모두 조회")
    void t1(){
        //모두 조회
        List<Question> list = repository.findAll();
        Question q = list.get(0);
        Assertions.assertThat(q.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("질문 id 찾기")
    void t2(){
        Optional<Question> oq = repository.findById(1);
        if(oq.isPresent()){
            Question qq = oq.get();
            Assertions.assertThat(qq.getSubject()).isEqualTo("sbb가 무엇인가요?");
        }
    }

    @Test
    @DisplayName("질문 제목 찾기")
    void t3(){
        Question q = repository.findBySubject("sbb가 무엇인가요?").orElse(null);
        Assertions.assertThat(q.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    @DisplayName("질문 제목 찾기")
    void t4(){
        //제목 검색
        List<Question> search = repository.findBySubjectLike("sbb%");
        Question q = search.get(0);
        Assertions.assertThat(q.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    @DisplayName("질문 수정하기")
    void t5(){
        int i = repository.findAll().get(0).getId();
        Optional<Question> opp = repository.findById(i);
        assertTrue(opp.isPresent());
        Question q5 = opp.get();
        q5.setContent("sbb에 대한 수정된 질문");
        repository.save(q5);
    }

    @Test
    @DisplayName("질문에 답변 생성하기")
    @Transactional
    void t6(){
        int i = repository.findAll().get(0).getId();
        Question q = repository.findById(i).get();
        answerRepository.save(
                Answer.builder()
                        .content("네. 자동으로 생성됩니다.")
                        .question(q)
                        .createTime(LocalDateTime.now())
                        .build());
        Answer a1 = answerRepository.findById(1).orElse(null);
        assertEquals(i, q.getId());
        List<Answer> answers= a1.getQuestion().getAnswers();
        assertEquals(answers.size(), 1);
        assertEquals("네. 자동으로 생성됩니다.", answers.get(0).getContent());
    }

    @Test
    @DisplayName("대량의 데이터 생성")
    void t8(){

    }
}
