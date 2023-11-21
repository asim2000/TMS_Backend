package com.example.task.controller;

import com.example.task.entity.Priority;
import com.example.task.entity.Progress;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.SuccessDataResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("priority")
public class PriorityController {
    @GetMapping("getAll")
    public DataResult<List<Priority>> getAll(){
        return new SuccessDataResult<>(Arrays.stream(Priority.values()).toList());
    }
}
