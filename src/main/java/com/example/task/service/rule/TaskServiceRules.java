package com.example.task.service.rule;

import com.example.task.entity.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import com.example.task.utils.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceRules {
    private final TaskRepository taskRepository;
    public void checkIfTaskIsNull(Task task) {
        if(task == null){
            throw new ServiceException(StatusCode.TASK_NOT_FOUND, Message.TASK_NOT_FOUND);
        }
    }

    public void checkIfTasksIsNullOrEmpty(List<Task> tasks) {
        if(tasks == null || tasks.isEmpty()){
            throw new ServiceException(StatusCode.TASK_NOT_FOUND, Message.TASK_NOT_FOUND);
        }
    }
}
