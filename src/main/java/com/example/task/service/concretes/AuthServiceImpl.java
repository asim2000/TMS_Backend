package com.example.task.service.concretes;

import com.example.task.entity.Status;
import com.example.task.entity.User;
import com.example.task.repository.UserRepository;
import com.example.task.security.JwtTokenProvider;
import com.example.task.service.abstracts.AuthService;
import com.example.task.service.abstracts.EmailService;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import com.example.task.service.dto.auth.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import com.example.task.service.result.SuccessResult;
import com.example.task.service.rule.AuthServiceRules;
import com.example.task.service.rule.UserServiceRules;
import com.example.task.utils.exceptions.ServiceException;
import com.example.task.utils.mappers.ModelMapperService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final PasswordEncoder passwordEncoder;
    final ModelMapperService modelMapperService;
    final UserRepository userRepository;
    final AuthServiceRules authServiceRules;
    final AuthenticationManager authenticationManager;
    final JwtTokenProvider jwtTokenProvider;
    final TokenBasedRememberMeServices tokenBasedRememberMeServices;
    final EmailService emailService;
    @Override
    public DataResult<String> login(LoginRequest loginRequest, HttpServletRequest req, HttpServletResponse res) {
        authServiceRules.checkUserOnLogin(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        if(loginRequest.isRememberMe()){
            tokenBasedRememberMeServices.loginSuccess(req,res,auth);
        }
        String jwtToken = jwtTokenProvider.generateJwtToken(modelMapperService.forRequest().map(userRepository.findByEmailOrUsernameAndStatus(loginRequest.getUsername(),loginRequest.getUsername(), Status.ACTIVE),User.class));
        return new DataResult<>(jwtToken, StatusCode.SUCCESSFULLY_LOGIN, Message.SUCCESSFULLY_LOGIN);
    }

    @Override
    public Result register(RegisterRequest request) throws MessagingException {
        User u = userRepository.findByEmailOrUsernameAndStatus(request.getEmail(),request.getUsername(),Status.ACTIVE);
        if(u != null){
            throw new ServiceException(StatusCode.USER_EXISTS,Message.USER_EXISTS);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = modelMapperService.forRequest().map(request,User.class);
        userRepository.save(user);
        String token = jwtTokenProvider.generateJwtToken(user);
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <form action="http://localhost:8085/api/auth/confirmAccount" method="post">
                        <input name="token" value=%s type="hidden"/>
                        <input name="userId" value=%s type="hidden"/>
                        <button type="submit">Confirm Account</button>
                    </form>
                </body>
                </html>
                """;
        //String html = "<a href='http://localhost:8085/api/auth/confirmAccount?userId=%s&token=%s'>Please click to verify your account</a>";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    emailService.sendHtmlEmail(request.getEmail(),"Confirm Account",String.format(html,token,user.getId()));
                } catch (MessagingException e) {
                    throw new ServiceException(StatusCode.MessagingException,e.getMessage());
                }
            }
        });
        thread.start();
        return new Result(StatusCode.SUCCESS,"The verification link has been sent to your email");
    }

    @Override
    public Result forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        authServiceRules.checkIfUsernameNotExists(forgotPasswordRequest.getEmail());
        User user = userRepository.findByEmailAndStatus(forgotPasswordRequest.getEmail(),Status.ACTIVE);
        String token = jwtTokenProvider.generateJwtToken(user,86400000L);
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <form action="http://localhost:8085/api/auth/resetPassword" method="post">
                        <input name="token" value=%s type="hidden"/>
                        <input name="username" value=%s id="username" type="hidden"/>
                        <label for="password">New Password</label>
                        <input name="password" id="password" type="password"/>
                        <button type="submit">Reset Password</button>
                    </form>
                </body>
                </html>
                """;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    emailService.sendHtmlEmail(forgotPasswordRequest.getEmail(),"Reset password", String.format(html,token,forgotPasswordRequest.getEmail()));
                } catch (MessagingException e) {
                    throw new ServiceException(StatusCode.MessagingException,e.getMessage());
                }
            }
        });
        thread.start();
        return new Result(StatusCode.SUCCESS,"Password reset form has been sent to your email");
    }

    @Override
    public Result resetPassword(ResetPasswordRequest resetPasswordRequest) {
        authServiceRules.checkIfInvalidToken(resetPasswordRequest.getToken());
        User user = userRepository.findByEmailOrUsernameAndStatus(resetPasswordRequest.getUsername(),resetPasswordRequest.getUsername(),Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);
        return new SuccessResult();
    }

    @Override
    public Result confirmAccount(ConfirmAccountRequest confirmAccountRequest) {
        User user = userRepository.findById(confirmAccountRequest.getUserId()).get();
        authServiceRules.checkUserStatus(user);
        if(jwtTokenProvider.validateToken(confirmAccountRequest.getToken())){
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            return new SuccessResult();
        }
        throw new ServiceException(StatusCode.INVALID_TOKEN,Message.INVALID_TOKEN);
    }
}
