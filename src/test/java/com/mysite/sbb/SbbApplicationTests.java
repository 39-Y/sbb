package com.mysite.sbb;

import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.Answer.entity.AnswerRepository;
import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.Question.Entity.QuestionRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    QuestionRepository repository;
    @Autowired
    AnswerRepository answerRepository;
    @Transactional
    @Test
    void testJpa() {
        //저장하기
        Question q1 = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .createDate(LocalDateTime.now())
                .build();
        repository.save(q1);

        Question q2 = Question.builder()
                .subject("spring 모델이 무엇인가요?")
                .content("spring은 id 자동생성 하나요.")
                .createDate(LocalDateTime.now())
                .build();
        repository.save(q2);

        //모두 조회
        List<Question> list = repository.findAll();
        Question q = list.get(0);
        Assertions.assertThat(q.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
        Assertions.assertThat(list.size()).isEqualTo(2);

        //id 찾기
        Optional<Question> oq = repository.findById(1);
        if(oq.isPresent()){
            Question qq = oq.get();
            Assertions.assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
        }

        //제목 찾기
        Question qqq = repository.findBySubject("sbb가 무엇인가요?").orElse(null);
        Assertions.assertThat(qqq.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");

        //제목 검색
        List<Question> search = repository.findBySubjectLike("sbb%");
        Question qqqq = search.get(0);
        Assertions.assertThat(qqqq.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");

        //수정
        Optional<Question> opp = repository.findById(1);
        assertTrue(opp.isPresent());
        Question q5 = opp.get();
        q5.setContent("sbb에 대한 수정된 질문");
        repository.save(q5);

        //삭제
//        assertEquals(2, repository.count());
//        Optional<Question> op3 = repository.findById(1);
//        assertTrue(op3.isPresent());
//        Question q6 = op3.get();
//        repository.delete(q6);
//        assertEquals(1, repository.count());

        //답변 데이터 생성
        answerRepository.save(
                Answer.builder()
                        .content("네. 자동으로 생성됩니다.")
                        .question(q2)
                        .createTime(LocalDateTime.now())
                        .build());
        Answer a1 = answerRepository.findById(1).orElse(null);
        assertEquals(2, a1.getQuestion().getId());

        //질문에서 답변 보기
        Question q7 = repository.findById(2).orElse(null);
        List<Answer> answers= q7.getAnswers();
        assertEquals(answers.size(), 1);
        assertEquals("네. 자동으로 생성됩니다.", answers.get(0).getContent());
    }

}
