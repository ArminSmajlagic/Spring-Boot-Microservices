package com.microservices.products.Services;

import com.microservices.products.Models.Category;
import com.microservices.products.Requests.CreateCategoryRequest;
import com.microservices.products.Requests.PatchCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    @Override
    public List<Category> GetCategories() {
        return null;
    }

    @Override
    public Category GetCategoryById(Integer id) {
        return null;
    }

    @Override
    public int DeleteCategory(Integer id) {
        return 0;
    }

    @Override
    public Category CreateCategory(CreateCategoryRequest request) {
        return null;
    }

    @Override
    public Category PatchCategory(PatchCategoryRequest request) {
        return null;
    }
}
