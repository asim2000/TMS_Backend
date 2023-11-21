package com.example.task.service.abstracts;

import com.example.task.service.dto.auth.*;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    DataResult<String> login(LoginRequest loginRequest, HttpServletRequest req, HttpServletResponse res);
    Result register(RegisterRequest request) throws MessagingException;

    Result forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;

    Result resetPassword(ResetPasswordRequest resetPasswordRequest);

    Result confirmAccount(ConfirmAccountRequest confirmAccountRequest);
}
