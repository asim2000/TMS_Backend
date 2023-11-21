package com.example.task.service.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataResult<T> extends Result {
    private T data;
    public DataResult(T data,Integer code,String message){
        super(code,message);
        this.data = data;
    }
}
