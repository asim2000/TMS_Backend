package com.example.task.service.dto.task;

import com.example.task.entity.Priority;
import com.example.task.entity.Progress;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTaskRequest {
    Integer categoryId;
    Priority priority;
    String title;
    String description;
    LocalDate deadline;
}
