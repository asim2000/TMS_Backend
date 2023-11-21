package com.example.task.service.result;

import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;

public class SuccessDataResult<T> extends DataResult<T> {
    public SuccessDataResult(T data){
        super(data, StatusCode.SUCCESS, Message.SUCCESS);
    }

}
