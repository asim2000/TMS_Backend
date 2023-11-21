package com.example.task.repository;

import com.example.task.entity.Status;
import com.example.task.entity.Task;
import com.example.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    Task findByIdAndStatus(Integer id, Status status);
    @Query("select t from Task t where t.category.user=:user and t.status=:status")
    List<Task> findAllByUserAndStatus(User user, Status status);
}
