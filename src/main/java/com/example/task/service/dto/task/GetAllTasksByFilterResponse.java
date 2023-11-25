package com.example.task.service.dto.task;

import com.example.task.entity.Category;
import com.example.task.entity.Priority;
import com.example.task.entity.Progress;
import com.example.task.service.dto.category.GetAllCategoryResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllTasksByFilterResponse {
    Integer id;
    GetAllCategoryResponse category;
    Priority priority;
    Progress progress;
    String title;
    String description;
    LocalDate deadline;
}
