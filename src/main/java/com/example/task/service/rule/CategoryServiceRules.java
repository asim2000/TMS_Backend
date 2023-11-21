package com.example.task.service.rule;

import com.example.task.entity.Category;
import com.example.task.service.constant.Message;
import com.example.task.service.constant.StatusCode;
import com.example.task.utils.exceptions.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class CategoryServiceRules {
    public void checkIfCategoryIsNull(Category category){
        if(category == null){
            throw new ServiceException(StatusCode.CATEGORY_NOT_FOUND, Message.CATEGORY_NOT_FOUND);
        }
    }
}
