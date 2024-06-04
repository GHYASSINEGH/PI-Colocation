package com.CoAndCo.Ghodhbani_Yassine.repositories;

import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Likes;
import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {
  Likes findByUserAndAnnouncement(User user, Announcement announcement);
}
