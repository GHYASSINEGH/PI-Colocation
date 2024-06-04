package com.CoAndCo.Ghodhbani_Yassine.controllers;

import com.CoAndCo.Ghodhbani_Yassine.configs.HttpResponse;
import com.CoAndCo.Ghodhbani_Yassine.dtos.AnnouncementDto;

import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Comment;
import com.CoAndCo.Ghodhbani_Yassine.entities.Likes;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import com.CoAndCo.Ghodhbani_Yassine.exceptions.EmptyFileException;
import com.CoAndCo.Ghodhbani_Yassine.repositories.AnnouncementRepository;
import com.CoAndCo.Ghodhbani_Yassine.repositories.UserRepository;
import com.CoAndCo.Ghodhbani_Yassine.services.AnnouncementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin( value="*" )
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper customObjectMapper;
    @Autowired
    AnnouncementRepository announcementRepository;


    //Les Posts

    @PostMapping("/addPostWithPhoto")
    public ResponseEntity<?> addPost(@RequestPart String announcementDto, @RequestPart MultipartFile file) throws IOException {

        if (file.isEmpty()) throw new EmptyFileException("File cannot be empty, please send a file!");

        try {
            AnnouncementDto obj = customObjectMapper.readValue(announcementDto, AnnouncementDto.class);

            // Save the Announcement
            AnnouncementDto newPost = announcementService.addAnnouncementWithPhotos(obj, file);

            return ResponseEntity.created(URI.create("")).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("announcement", newPost))
                            .message("Announcement created")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build()
            );
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }






    @PostMapping("/addAnnouncement/{userId}")
    public Announcement addPost(@PathVariable("userId") long userId,@RequestBody Announcement announcement) {
        return announcementService.addAnnouncement(announcement,userId);
    }

    @PutMapping("/updatePost/{postId}")
    public Announcement updatePost(@RequestBody Announcement announcement, @PathVariable("postId") long postId) {
        return announcementService.updateAnnouncement(announcement, postId);
    }
    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable("postId") long postId) {
        announcementService.removeAnnouncement(postId);
    }

    @GetMapping("/allPost")
    public List<Announcement> getPosts() {
        return announcementService.retrieveAllAnnouncements();
    }

    @GetMapping("postby/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") long id) {
        Announcement a= announcementService.retrieveAnnouncementById(id).get();

        return ResponseEntity.ok(a);
    }



    //PostLikeee

    @PostMapping("/{postId}/{userId}/like")
    public void likePost(@PathVariable("postId") long postId,@PathVariable("userId") long userId) {
        announcementService.likeAnnonce(postId,userId);
    }

    @GetMapping("/allPostlikes")
    public List<Likes> getPostLikes() {
        return announcementService.getPostLikes();
    }

    @GetMapping("/postlike-by/{id}")
    public Optional<Likes> getPostLikeById(@PathVariable("id") long id) {
        return announcementService.getPostLikeById(id);
    }

    @GetMapping("/top-liked")
    public List<Announcement> topLikedPosts() {
        return announcementService.topLikedAnnonces();
    }


    //Les commentaires


    @PostMapping("/{postId}/{userId}/addComment")
    public Comment commentPost(@PathVariable("postId") long postId,@PathVariable("userId") long userId, @RequestBody Comment comment) {
        return announcementService.commentAnnouncement(postId,userId,comment);
    }

    @GetMapping("/allComment/{postId}")
    public List<Comment> getPostComments( @PathVariable("postId") long postId) {
        return announcementService.getPostComments(postId);
    }

    @GetMapping("comment/{id}")
    public Optional<Comment> getPostCommentById(@PathVariable("id") long id) {
        return announcementService.getPostCommentById(id);
    }

    @GetMapping("userById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Announcement>> searchAnnouncements(@RequestParam("keyword") String keyword) {
        List<Announcement> announcements = announcementService.searchAnnouncements(keyword);
        if (announcements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(announcements);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id ,@RequestBody AnnouncementDto model) {
        Announcement announcement = announcementRepository.findById(id).get();
        announcement.setTitle(model.getTitle());
        announcement.setContent(model.getContent());
        announcement.setLocation(model.getLocation());
        announcement.setPosteDate(LocalDateTime.now());

        return ResponseEntity.ok(announcementRepository.save(announcement));
    }


    @GetMapping("/details/likePercentage")
    public ResponseEntity<?> getProjectProgression() {
        try {


            int totalLikes = 0;
            int LikedPosts = 0;

            List<Announcement> posts = announcementRepository.findAll();
            for (Announcement announcement : posts) {
                totalLikes ++;

                if (announcement.getLikes() != 0) {
                    LikedPosts++;
                }
            }
            int score = 0;
            if (totalLikes > 0) {
                score = (LikedPosts * 100) / totalLikes;
            }

            return ResponseEntity.ok(score);
        } catch (Exception e) {
            return ResponseEntity.ok("Progression Not Calculated");
        }
    }

}
