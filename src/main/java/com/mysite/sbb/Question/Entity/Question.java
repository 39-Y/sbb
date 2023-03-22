package com.mysite.sbb.Question.Entity;

import com.mysite.sbb.Answer.entity.Answer;
import com.mysite.sbb.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(length = 200)
    private String subject;
    private LocalDateTime createDate;
    @ManyToOne
    private SiteUser author;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

}
