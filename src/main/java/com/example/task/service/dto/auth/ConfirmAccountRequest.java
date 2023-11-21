package com.example.task.service.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmAccountRequest {
    @NotNull
    @NotEmpty
    Integer userId;
    @NotNull
    @NotEmpty
    String token;
}
