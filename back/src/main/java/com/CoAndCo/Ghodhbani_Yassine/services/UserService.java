package com.CoAndCo.Ghodhbani_Yassine.services;

import com.CoAndCo.Ghodhbani_Yassine.dtos.UserDto;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    UserDto addUser(User user, MultipartFile file) throws IOException, ConstraintViolationException;

    //the normal method did work no need for this one
    List<UserDto> getAllUsers(User client) throws UnsupportedEncodingException;

    UserDto getUserById(Long userId) throws UnsupportedEncodingException;

    //the normal method did work no need for this one

}
