package com.CoAndCo.Ghodhbani_Yassine.dtos;

import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CommentModel {

    private String content;

    private LocalDateTime createdDate = LocalDateTime.now();

    long announcement;

    long user;
}
