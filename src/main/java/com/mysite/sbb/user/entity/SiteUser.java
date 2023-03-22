package com.mysite.sbb.user.entity;

import com.mysite.sbb.Question.Entity.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;

//    @OneToMany(mappedBy = "author")
//    List<Question> questions = new ArrayList<>();
}
