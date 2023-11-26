package com.example.task.service.concretes;

import com.example.task.entity.Category;
import com.example.task.entity.Status;
import com.example.task.entity.User;
import com.example.task.repository.CategoryRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.abstracts.CategoryService;
import com.example.task.service.dto.category.CreateCategoryRequest;
import com.example.task.service.dto.category.GetAllCategoryResponse;
import com.example.task.service.result.DataResult;
import com.example.task.service.result.Result;
import com.example.task.service.result.SuccessDataResult;
import com.example.task.service.result.SuccessResult;
import com.example.task.service.rule.UserServiceRules;
import com.example.task.utils.mappers.ModelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final UserRepository userRepository;
    private final UserServiceRules userServiceRules;

    @Override
    public Result create(CreateCategoryRequest createCategoryRequest) {
        User user = userRepository.findByIdAndStatus(createCategoryRequest.getUserId(), Status.ACTIVE);
        userServiceRules.checkIfUserIsNull(user);
        Category category = modelMapperService.forRequest().map(createCategoryRequest, Category.class);
        category.getUser().setId(user.getId());
        category.setId(null);
        categoryRepository.save(category);
        return new SuccessResult();
    }

    @Override
    public DataResult<List<GetAllCategoryResponse>> getAll(Integer userId) {
        User user = userRepository.findByIdAndStatus(userId,Status.ACTIVE);
        userServiceRules.checkIfUserIsNull(user);
        List<Category> categories = categoryRepository.findAllByUserAndStatus(user,Status.ACTIVE);
        return new SuccessDataResult<>(categories.stream().map(category -> modelMapperService.forResponse().map(category,GetAllCategoryResponse.class)).collect(Collectors.toList()));
    }
}
