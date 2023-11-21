package com.example.task.security;

import com.example.task.entity.Status;
import com.example.task.repository.UserRepository;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.task.entity.User user = null;
        if(username.contains("@")){
            user = userRepository.findByEmailAndStatus(username, Status.ACTIVE);
            if(user == null) {
                throw new SecurityException(StatusCode.USER_EMAIL_NOT_FOUND, Message.USER_EMAIL_NOT_FOUND);
            }
        }else{
            user = userRepository.findByUsernameAndStatus(username, Status.ACTIVE);
            if(user == null) {
                throw new SecurityException(StatusCode.USERNAME_NOT_FOUND, Message.USERNAME_NOT_FOUND);
            }
        }

        return new User(username, user.getPassword(),new ArrayList<>());
    }

    public UserDetails loadUserById(Integer id) {
        com.example.task.entity.User user = userRepository.findById(id).get();
        return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
    }

}