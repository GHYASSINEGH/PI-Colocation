package com.CoAndCo.Ghodhbani_Yassine.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long idUser;

    private String fullName;

    private String email;

    private String password;

    private String profilePicture;
    private String photoUrl;

    public UserDto(String fullName, String email, String profilePicture, String photoUrl) {
        this.fullName=fullName;
        this.email=email;
        this.profilePicture=profilePicture;
        this.photoUrl=photoUrl;
    }
}
