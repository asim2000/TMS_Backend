package com.example.task.service.abstracts;

import com.example.task.service.dto.task.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;

import java.util.List;

public interface TaskService {
    Result create(CreateTaskRequest createTaskRequest);
    Result update(UpdateTaskRequest updateTaskRequest);
    Result delete(Integer id);
    DataResult<GetTaskByIdResponse> getTaskById(Integer taskId);
    DataResult<List<GetAllTasksByFilterResponse>> getAll(FilterTaskRequest filterTaskRequest);
}
