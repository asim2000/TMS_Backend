package com.example.task.controller;

import com.example.task.service.abstracts.AuthService;
import com.example.task.service.dto.auth.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {
    private final AuthService authService;
    @PostMapping("login")
    public DataResult<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @PostMapping("register")
    public Result register(@RequestBody @Valid RegisterRequest request) throws MessagingException{
        return authService.register(request);
    }
    @PostMapping("forgotPassword")
    public Result forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        return authService.forgotPassword(forgotPasswordRequest);
    }
    @PostMapping("resetPassword")
    public Result resetPassword(@ModelAttribute ResetPasswordRequest resetPasswordRequest){
        return authService.resetPassword(resetPasswordRequest);
    }
    @PostMapping("confirmAccount")
    public Result ConfirmAccount(@ModelAttribute ConfirmAccountRequest confirmAccountRequest){
        return authService.confirmAccount(confirmAccountRequest);
    }


}
