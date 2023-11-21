package com.example.task.service.dto.task;

import com.example.task.entity.Priority;
import com.example.task.entity.Progress;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterTaskRequest {
    Integer userId;
    Integer categoryId;
    Priority priority;
    Progress progress;
}
