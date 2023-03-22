package com.mysite.sbb.Answer.entity;

import com.mysite.sbb.Question.Entity.Question;
import com.mysite.sbb.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    @ManyToOne
    private SiteUser author;
    @ManyToOne
    private Question question;
}
