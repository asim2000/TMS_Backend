package com.example.task.service.rule;

import com.example.task.entity.Status;
import com.example.task.entity.User;
import com.example.task.repository.UserRepository;
import com.example.task.security.JwtTokenProvider;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import com.example.task.service.result.Result;
import com.example.task.service.result.SuccessResult;
import com.example.task.utils.exceptions.ServiceException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthServiceRules {
    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;
    public void checkIfUsernameNotExists(String username){
        if(username.contains("@")){
            if(userRepository.findByEmailAndStatus(username, Status.ACTIVE) == null){
                throw new ServiceException(StatusCode.USER_EMAIL_NOT_FOUND, Message.USER_EMAIL_NOT_FOUND);
            }
        }else{
            User user = userRepository.findByUsernameAndStatus(username,Status.ACTIVE);
            if(user == null){
                throw new ServiceException(StatusCode.USERNAME_NOT_FOUND, Message.USERNAME_NOT_FOUND);
            }
        }
    }
    public void checkUserOnLogin(String username){
        User user = userRepository.findByEmailOrUsername(username,username);
        if(user == null){
            throw new ServiceException(StatusCode.USER_NOT_FOUND,Message.USER_NOT_FOUND);
        }
        else if(user.getStatus() == Status.PENDING){
            throw new ServiceException(StatusCode.EMAIL_NOT_CONFIRMED,Message.EMAIL_NOT_CONFIRMED);
        }
    }
    public void checkIfInvalidToken(String token) {
        if(!jwtTokenProvider.validateToken(token)){
            throw new ServiceException(StatusCode.INVALID_TOKEN,Message.INVALID_TOKEN);
        }
    }

    public void checkUserStatus(User user) {
        if(user == null || user.getStatus() == Status.DEACTIVE){
            throw new ServiceException(StatusCode.USER_NOT_FOUND,Message.USER_NOT_FOUND);
        }
        else if(user.getStatus()==Status.ACTIVE){
            throw new ServiceException(423,"User is active.Please login.");
        }
    }
}
