package com.example.itog.repositories;

import com.example.itog.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUsers, Long> {
    Optional<AppUsers> findByEmail(String email);
}
