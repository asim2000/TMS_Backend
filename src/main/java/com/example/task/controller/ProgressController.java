package com.example.task.controller;

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
@RequestMapping("progress")
public class ProgressController {
    @GetMapping("getAll")
    public DataResult<List<Progress>> getAll(){
        return new SuccessDataResult<>(Arrays.stream(Progress.values()).toList());
    }
}
