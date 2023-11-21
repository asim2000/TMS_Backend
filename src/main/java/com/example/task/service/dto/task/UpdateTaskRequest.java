package com.example.task.service.dto.task;

import com.example.task.entity.Category;
import com.example.task.entity.Priority;
import com.example.task.entity.Progress;
import com.example.task.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTaskRequest {
    Integer id;
    Integer categoryId;
    String title;
    String description;
    LocalDate deadline;
    Priority priority;
    Progress progress;
}
