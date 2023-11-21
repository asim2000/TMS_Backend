package com.example.task.service.concretes;

import com.example.task.entity.Category;
import com.example.task.entity.Status;
import com.example.task.entity.Task;
import com.example.task.entity.User;
import com.example.task.repository.CategoryRepository;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.abstracts.TaskService;
import com.example.task.service.dto.task.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import com.example.task.service.result.SuccessDataResult;
import com.example.task.service.result.SuccessResult;
import com.example.task.service.rule.CategoryServiceRules;
import com.example.task.service.rule.TaskServiceRules;
import com.example.task.service.rule.UserServiceRules;
import com.example.task.utils.mappers.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final TaskServiceRules taskServiceRule;
    private final UserServiceRules userServiceRules;
    private final CategoryServiceRules categoryServiceRules;
    @Override
    public Result create(CreateTaskRequest createTaskRequest) {
        Category category = categoryRepository.findByIdAndStatus(createTaskRequest.getCategoryId(),Status.ACTIVE);
        categoryServiceRules.checkIfCategoryIsNull(category);
        Task task = modelMapperService.forRequest().map(createTaskRequest, Task.class);
        task.setCategory(category);
        task.setId(null);
        taskRepository.save(task);
        return new SuccessResult();
    }

    @Override
    public Result update(UpdateTaskRequest updateTaskRequest) {
        Task task = taskRepository.findByIdAndStatus(updateTaskRequest.getId(),Status.ACTIVE);
        taskServiceRule.checkIfTaskIsNull(task);
        task.setCategory(categoryRepository.findByIdAndStatus(updateTaskRequest.getCategoryId(), Status.ACTIVE));
        task.setPriority(updateTaskRequest.getPriority());
        task.setProgress(updateTaskRequest.getProgress());
        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());
        task.setDeadline(updateTaskRequest.getDeadline());
        taskRepository.save(task);
        return new SuccessResult();
    }

    @Override
    public Result delete(Integer id) {
        Task task = taskRepository.findByIdAndStatus(id,Status.ACTIVE);
        taskServiceRule.checkIfTaskIsNull(task);
        task.setStatus(Status.DEACTIVE);
        taskRepository.save(task);
        return new SuccessResult();
    }

    @Override
    public DataResult<GetTaskByIdResponse> getTaskById(Integer taskId) {
        Task task = taskRepository.findByIdAndStatus(taskId,Status.ACTIVE);
        taskServiceRule.checkIfTaskIsNull(task);
        return new SuccessDataResult<GetTaskByIdResponse>(modelMapperService.forResponse().map(task,GetTaskByIdResponse.class));
    }

    @Override
    public DataResult<List<GetAllTasksByFilterResponse>> getAll(FilterTaskRequest filterTaskRequest) {
        User user = userRepository.findByIdAndStatus(filterTaskRequest.getUserId(),Status.ACTIVE);
        userServiceRules.checkIfUserIsNull(user);
        List<Task> tasks = taskRepository.findAllByUserAndStatus(user,Status.ACTIVE);
        taskServiceRule.checkIfTasksIsNullOrEmpty(tasks);

        if(filterTaskRequest.getCategoryId() != null){
            List<Task> newList = new ArrayList<>();
            tasks.stream().forEach(task->{
                if(task.getCategory().getId() == filterTaskRequest.getCategoryId())
                    newList.add(task);
            });
            tasks = newList;
        }
        taskServiceRule.checkIfTasksIsNullOrEmpty(tasks);
        if(filterTaskRequest.getPriority() != null){
            List<Task> newList = new ArrayList<>();
            tasks.stream().forEach(task->{
                if(task.getPriority() == filterTaskRequest.getPriority())
                    newList.add(task);
            });
            tasks = newList;
        }
        taskServiceRule.checkIfTasksIsNullOrEmpty(tasks);
        if(filterTaskRequest.getProgress() != null){
            List<Task> newList = new ArrayList<>();
            tasks.stream().forEach(task->{
                if(task.getProgress() == filterTaskRequest.getProgress())
                    newList.add(task);
            });
            tasks = newList;
        }
        taskServiceRule.checkIfTasksIsNullOrEmpty(tasks);
        return new SuccessDataResult<>(tasks.stream().map(task -> modelMapperService.forResponse().map(task,GetAllTasksByFilterResponse.class)).collect(Collectors.toList()));
    }
}
