package com.CoAndCo.Ghodhbani_Yassine.services;

import com.CoAndCo.Ghodhbani_Yassine.dtos.UserDto;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import com.CoAndCo.Ghodhbani_Yassine.exceptions.FileExistException;
import com.CoAndCo.Ghodhbani_Yassine.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Value("${project.photo}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;
@Autowired
FileService fileService;
@Autowired
    UserRepository userRepository;

    @Override
    public UserDto addUser(User user, MultipartFile file) throws IOException, ConstraintViolationException {

        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistException("File already exists! Please give another file!");
        }
        String uploadedFileName = fileService.uploadFile(path, file);
        String photoUrl = baseUrl + "/file/" + uploadedFileName;

        user.setProfilePicture(uploadedFileName);
        user.setPhotoUrl(photoUrl); // Set the photoUrl






        User savedUser = userRepository.save(user);



        var responseObj = new UserDto(
                savedUser.getIdUser(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getProfilePicture(),
                savedUser.getPhotoUrl()
        );



        return responseObj;
    }

    //the normal method did work no need for this one
    @Override
    public List<UserDto> getAllUsers(User client) throws UnsupportedEncodingException {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            String profilePicture = user.getProfilePicture();
            String photoUrl = profilePicture = baseUrl + "/file/" + URLEncoder.encode(profilePicture, StandardCharsets.UTF_8.toString()) ;

            UserDto userDto = new UserDto(
                    user.getFullName(),
                    user.getEmail(),

                    profilePicture,
                    photoUrl
            );

            userDtos.add(userDto);
        }

        return userDtos;
    }


    @Override
    public UserDto getUserById(Long userId) throws UnsupportedEncodingException {
        User user = userRepository.findById(userId).get();
        List<UserDto> userDtos = new ArrayList<>();


            String profilePicture = user.getProfilePicture();
            String photoUrl = profilePicture = baseUrl + "/file/" + URLEncoder.encode(profilePicture, StandardCharsets.UTF_8.toString()) ;

            UserDto userDto = new UserDto(
                    user.getFullName(),
                    user.getEmail(),

                    profilePicture,
                    photoUrl
            );



        return userDto;
    }
}
