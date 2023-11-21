package com.example.task.service.result;

import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;

public class SuccessResult extends Result {
    public SuccessResult(){
        super(StatusCode.SUCCESS, Message.SUCCESS);
    }
}
