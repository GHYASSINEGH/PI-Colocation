package com.CoAndCo.Ghodhbani_Yassine.services;

import com.CoAndCo.Ghodhbani_Yassine.dtos.AnnouncementDto;
import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Comment;
import com.CoAndCo.Ghodhbani_Yassine.entities.Likes;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    List<Announcement> searchAnnouncements(String keyword);

    List<Announcement> retrieveAllAnnouncements();

    Announcement  addAnnouncement(Announcement  a,Long userId);

    Announcement updateAnnouncement (Announcement a , Long id );

    AnnouncementDto addAnnouncementWithPhotos(AnnouncementDto a, MultipartFile file) throws IOException, ConstraintViolationException;

    Optional<Announcement> retrieveAnnouncementById (Long idAnnouncement);
    void removeAnnouncement(Long id);


    //Commentaire
    Comment commentAnnouncement(Long id, Long userId, Comment comment);

    List<Comment> getPostComments(Long PostId);

    Optional<Comment> getPostCommentById(Long id);

    // Like Post
    Likes likeAnnonce(Long announceId, Long idUser);

    List<Likes> getPostLikes();

    Optional<Likes> getPostLikeById(Long id);

    List<Announcement> topLikedAnnonces();
}
