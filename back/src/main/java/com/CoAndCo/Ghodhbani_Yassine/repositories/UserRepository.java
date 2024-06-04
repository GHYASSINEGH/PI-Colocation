package com.CoAndCo.Ghodhbani_Yassine.repositories;

import com.CoAndCo.Ghodhbani_Yassine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
