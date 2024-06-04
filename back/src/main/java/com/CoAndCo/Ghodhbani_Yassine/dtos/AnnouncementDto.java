package com.CoAndCo.Ghodhbani_Yassine.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private Long id;


    private String title;


    private String content;


    private String location;

    private LocalDateTime posteDate = LocalDateTime.now();
    private Integer likes=0;
    private String photo;
    private String photoUrl;
    private  long user;

    public AnnouncementDto(Long id, String title, String content, String location, LocalDateTime posteDate, Integer likes, String photo, String photoUrl) {
        this.id=id;
        this.title=title;
        this.content=content;
        this.location=location;
        this.photo=photo;
        this.photoUrl=photoUrl;
        this.posteDate=posteDate;
        this.likes=likes;
    }
}
