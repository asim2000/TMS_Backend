package com.example.task.controller;

import com.example.task.service.abstracts.CategoryService;
import com.example.task.service.dto.category.CreateCategoryRequest;
import com.example.task.service.dto.category.GetAllCategoryResponse;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryController {
    final CategoryService categoryService;
    @PostMapping("create")
    public Result create(@RequestBody CreateCategoryRequest createCategoryRequest){
        return categoryService.create(createCategoryRequest);
    }
    @GetMapping("getAll/user/{userId}")
    public DataResult<List<GetAllCategoryResponse>> getAll(@PathVariable Integer userId){
        return categoryService.getAll(userId);
    }
}
