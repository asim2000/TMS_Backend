package com.example.task.service.abstracts;

import com.example.task.entity.Category;
import com.example.task.service.dto.category.CreateCategoryRequest;
import com.example.task.service.dto.category.GetAllCategoryResponse;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;

import java.util.List;

public interface CategoryService {
    Result create(CreateCategoryRequest createCategoryRequest);
    DataResult<List<GetAllCategoryResponse>> getAll(Integer userId);
}
