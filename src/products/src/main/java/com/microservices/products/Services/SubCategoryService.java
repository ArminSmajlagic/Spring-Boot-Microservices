package com.microservices.products.Services;

import com.microservices.products.Models.Category;
import com.microservices.products.Requests.CreateSubCategoryRequest;
import com.microservices.products.Requests.PatchSubCategoryRequest;

import java.util.List;

public class SubCategoryService implements ISubCategoryService{
    @Override
    public List<Category> GetSubCategories() {
        return null;
    }

    @Override
    public Category GetSubCategoryById(Integer id) {
        return null;
    }

    @Override
    public int DeleteSubCategory(Integer id) {
        return 0;
    }

    @Override
    public Category CreateSubCategory(CreateSubCategoryRequest request) {
        return null;
    }

    @Override
    public Category PatchSubCategory(PatchSubCategoryRequest request) {
        return null;
    }
}
