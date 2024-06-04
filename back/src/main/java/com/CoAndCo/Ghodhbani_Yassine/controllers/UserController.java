package com.CoAndCo.Ghodhbani_Yassine.controllers;

import com.CoAndCo.Ghodhbani_Yassine.configs.HttpResponse;
import com.CoAndCo.Ghodhbani_Yassine.dtos.UserDto;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import com.CoAndCo.Ghodhbani_Yassine.exceptions.EmptyFileException;
import com.CoAndCo.Ghodhbani_Yassine.repositories.UserRepository;
import com.CoAndCo.Ghodhbani_Yassine.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin( value="*" )
public class UserController {
    @Autowired
    UserService userService;
@Autowired
UserRepository userRepository;


    @PostMapping("/add-User")
    public ResponseEntity<?> addUserHandler(@RequestPart String userDto, @RequestPart MultipartFile file) throws IOException {

        if (file.isEmpty()) throw new EmptyFileException("File cannot be empty, please send a file!");

        try {
            UserDto obj = new ObjectMapper().readValue(userDto, UserDto.class);

            // Create a new User
            User user = new User();
            user.setFullName(obj.getFullName());
            user.setEmail(obj.getEmail());
            user.setPassword(obj.getPassword());

            // Save the User and update the Employee reference
            UserDto newUser = userService.addUser(user, file);





            return ResponseEntity.created(URI.create("")).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("user", newUser))
                            .message("User created")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build()
            );
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }


}
