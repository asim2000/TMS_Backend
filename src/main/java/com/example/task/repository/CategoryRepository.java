package com.example.task.repository;

import com.example.task.entity.Category;
import com.example.task.entity.Status;
import com.example.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByIdAndStatus(Integer categoryId, Status active);
    List<Category> findAllByUserAndStatus(User user,Status status);
}
