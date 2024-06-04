package com.CoAndCo.Ghodhbani_Yassine.services;

import com.CoAndCo.Ghodhbani_Yassine.dtos.AnnouncementDto;
import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Comment;
import com.CoAndCo.Ghodhbani_Yassine.entities.Likes;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import com.CoAndCo.Ghodhbani_Yassine.exceptions.FileExistException;
import com.CoAndCo.Ghodhbani_Yassine.repositories.AnnouncementRepository;
import com.CoAndCo.Ghodhbani_Yassine.repositories.CommentRepository;
import com.CoAndCo.Ghodhbani_Yassine.repositories.LikesRepository;
import com.CoAndCo.Ghodhbani_Yassine.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
//@AllArgsConstructor

public class AnnouncementServiceImpl implements AnnouncementService{

    @Value("${project.photo}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
     CommentRepository commentRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    FileService fileService;


    @Override
    public List<Announcement> searchAnnouncements(String keyword) {
        return announcementRepository.findByTitleOrContentContaining(keyword);
    }


    @Override
    public List<Announcement> retrieveAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement addAnnouncement(Announcement a,Long userId) {
        User user =userRepository.findById(userId).get();
        String[] contentWords = a.getContent().split(" ");

        String[] filteredPost = filterUnallowedWords(contentWords);
        String newContent="";
        for(String w: filteredPost) {
            newContent+=w+" ";
        }
        Announcement announcement=new Announcement();
        announcement.setTitle(a.getTitle());
        announcement.setPosteDate(LocalDateTime.now());
        announcement.setContent(newContent);
        announcement.setUser(user);
        return announcementRepository.save(announcement);
    }


    @Override
    public AnnouncementDto addAnnouncementWithPhotos(AnnouncementDto a, MultipartFile file) throws IOException, ConstraintViolationException {

        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistException("File already exists! Please give another file!");
        }
        String uploadedFileName = fileService.uploadFile(path, file);
        String photoUrl = baseUrl + "/file/" + uploadedFileName;

        User user = userRepository.findById(a.getUser()).orElseThrow(() -> new RuntimeException("User not found"));
        String[] contentWords = a.getContent().split(" ");
        String[] filteredPost = filterUnallowedWords(contentWords);
        String newContent = String.join(" ", filteredPost);

        Announcement announcement = new Announcement();
        announcement.setTitle(a.getTitle());
        announcement.setPosteDate(a.getPosteDate());
        announcement.setLocation(a.getLocation());
        announcement.setContent(newContent);
        announcement.setUser(user);
        announcement.setPhoto(uploadedFileName);
        announcement.setPhotoUrl(photoUrl);
        announcement.setLikes(0);

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return new AnnouncementDto(
                savedAnnouncement.getId(),
                savedAnnouncement.getTitle(),
                savedAnnouncement.getContent(),
                savedAnnouncement.getLocation(),
                savedAnnouncement.getPosteDate(),
                savedAnnouncement.getLikes(),
                savedAnnouncement.getPhoto(),
                savedAnnouncement.getPhotoUrl()
        );
    }





    @Override
    public Optional<Announcement> retrieveAnnouncementById(Long idAnnouncement) {
        return announcementRepository.findById(idAnnouncement);


    }

    @Override
    public void removeAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
    @Override
    public Announcement updateAnnouncement(Announcement a,Long id) {
        String[] contentWords = a.getContent().split(" ");

        String[] filteredPost = filterUnallowedWords(contentWords);
        String newContent="";
        for(String w: filteredPost) {
            newContent+=w+" ";
        }
      Announcement announcement=announcementRepository.findById(id).get();
        Long userId=announcement.getUser().getIdUser();
            announcement.setTitle(a.getTitle());
            announcement.setContent(a.getContent());
             announcement.setPosteDate(LocalDateTime.now());
             announcement.setContent(newContent);
            announcement.setUser(this.userRepository.findById(userId).orElse(null));


        return announcementRepository.save(announcement);
    }

    //Commentaire
    @Override
    public Comment commentAnnouncement(Long id, Long userId, Comment comment) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        Optional<User> user = userRepository.findById(userId);

        if (!announcement.isPresent() || !user.isPresent()) {
            throw new RuntimeException("Announcement or User not found");
        }

        String[] contentWords = comment.getContent().split(" ");
        String[] filteredPost = filterUnallowedWords(contentWords);

        // Join the filtered words back into a single string
        String newContent = String.join(" ", filteredPost);

        comment.setContent(newContent);
        comment.setUser(user.get());
        comment.setAnnouncement(announcement.get());

        return commentRepository.save(comment);

    }
    @Override
    public List<Comment> getPostComments(Long postId) {
        Announcement announcement=announcementRepository.findById(postId).get();

      List<Comment> comments =commentRepository.findByAnnouncement(announcement);



        return comments;
    }
    @Override
    public Optional<Comment> getPostCommentById(Long id) {
        return commentRepository.findById(id);
    }






    // Like Post
    @Override
    public Likes likeAnnonce(Long announceId, Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found with id " + idUser));
        Announcement announcement = announcementRepository.findById(announceId).orElseThrow(() -> new EntityNotFoundException("Post not found with id " + announceId));
        Likes postLike = likesRepository.findByUserAndAnnouncement(user, announcement);

        if (postLike != null) {
            if (postLike.isEtat()) {

                announcement.setLikes(announcement.getLikes() - 1);
                likesRepository.delete(postLike);
                return null;
            }
        }

        postLike = new Likes();
        postLike.setAnnouncement(announcement);
        postLike.setUser(user);
        postLike.setEtat(true);
        announcement.setLikes(announcement.getLikes() + 1);
        return likesRepository.save(postLike);
    }




@Override
    public List<Likes> getPostLikes() {
        return likesRepository.findAll();
    }
    @Override
    public Optional<Likes> getPostLikeById(Long id) {
        return likesRepository.findById(id);
    }

    @Override
    public List<Announcement> topLikedAnnonces() {

        return announcementRepository.findTop5ByOrderByLikesDesc();
    }






    public String[] filterUnallowedWords(String[] content) {



     //  org.json.simple.parser.JSONParser parser =new org.json.simple.parser.JSONParser();
        JSONParser parser = new JSONParser();

        try {
            // Load the JSON file from the resources folder
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("badWords.json");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource file not found");
            }

            // Parse the JSON file
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream));
            JSONArray wordsList = (JSONArray) jsonObject.get("badWords");

            // Filter and replace bad words
            for (int i = 0; i < content.length; i++) {
                String word = content[i];
                Iterator<?> iterator = wordsList.iterator();

                while (iterator.hasNext()) {
                    if (word.equalsIgnoreCase((String) iterator.next())) {
                        content[i] = "*****";
                        break; // Break the loop once a match is found to avoid unnecessary checks
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}


