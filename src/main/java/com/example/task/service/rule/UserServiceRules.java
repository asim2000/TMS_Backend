package com.example.task.service.rule;

import com.example.task.entity.User;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import com.example.task.utils.exceptions.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceRules {
    public void checkIfUserIsNull(User user){
        if(user == null){
            throw new ServiceException(StatusCode.USER_NOT_FOUND, Message.USER_NOT_FOUND);
        }
    }
}
