package com.CoAndCo.Ghodhbani_Yassine.repositories;

import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByAnnouncement(Announcement announcement);
}
