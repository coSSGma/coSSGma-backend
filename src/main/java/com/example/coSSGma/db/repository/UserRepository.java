package com.example.coSSGma.db.repository;

import com.example.coSSGma.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
