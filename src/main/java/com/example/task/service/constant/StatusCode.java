package com.example.task.service.constant;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC,makeFinal = true)
public class StatusCode {
    public static final Integer SUCCESS = 200;
    public static final Integer SUCCESSFULLY_LOGIN = 210;
    public static final Integer UN_AUTHORIZATION = 401;
    public static final Integer USER_EMAIL_NOT_FOUND = 402;
    public static final Integer USERNAME_NOT_FOUND = 403;
    public static final Integer VALIDATION_EXCEPTION = 410;
    public static final Integer INVALID_PASSWORD = 411;
    public static final Integer INVALID_TOKEN = 434;
    public static final Integer USER_NOT_FOUND = 413;
    public static final Integer TASK_NOT_FOUND = 414;
    public static final Integer CATEGORY_NOT_FOUND = 415;

    public static final Integer MessagingException = 657;
    public static final Integer USER_EXISTS = 634;
    public static final Integer EMAIL_NOT_CONFIRMED = 654;
}
