package com.example.task.repository;

import com.example.task.entity.Status;
import com.example.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsernameAndStatus(String username, Status status);
    User findByEmailAndStatus(String email, Status status);
    User findByEmailOrUsernameAndStatus(String email,String username,Status status);
    User findByEmailOrUsername(String email,String username);
    User findByIdAndStatus(Integer id,Status status);
}
