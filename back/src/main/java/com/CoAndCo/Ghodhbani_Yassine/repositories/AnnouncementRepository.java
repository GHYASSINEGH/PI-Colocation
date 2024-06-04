package com.CoAndCo.Ghodhbani_Yassine.repositories;

import com.CoAndCo.Ghodhbani_Yassine.entities.Announcement;
import com.CoAndCo.Ghodhbani_Yassine.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    List<Announcement> findTop5ByOrderByLikesDesc();



    @Query("SELECT a FROM Announcement a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Announcement> findByTitleOrContentContaining(@Param("keyword") String keyword);
}
