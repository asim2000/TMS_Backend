package com.example.task.controller;

import com.example.task.service.abstracts.TaskService;
import com.example.task.service.dto.task.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskController {
    final TaskService taskService;
    @PostMapping("create")
    public Result create(@RequestBody CreateTaskRequest createTaskRequest){
        return taskService.create(createTaskRequest);
    }
    @PostMapping("update")
    public Result update(@RequestBody UpdateTaskRequest updateTaskRequest){
        return taskService.update(updateTaskRequest);
    }
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        return taskService.delete(id);
    }
    @GetMapping("getById/{id}")
    public DataResult<GetTaskByIdResponse> getbyId(@PathVariable Integer id){
        return taskService.getTaskById(id);
    }
    @PostMapping("getAll")
    public DataResult<List<GetAllTasksByFilterResponse>> getAll(@RequestBody FilterTaskRequest filterTaskRequest){
        return taskService.getAll(filterTaskRequest);
    }
}
