package com.api.CyberSentry.repository;

import com.api.CyberSentry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
