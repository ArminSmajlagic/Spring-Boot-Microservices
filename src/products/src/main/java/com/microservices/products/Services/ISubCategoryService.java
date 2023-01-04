package com.microservices.products.Services;

import com.microservices.products.Models.Category;
import com.microservices.products.Requests.CreateSubCategoryRequest;
import com.microservices.products.Requests.PatchSubCategoryRequest;

import java.util.List;

public interface ISubCategoryService {

    List<Category> GetSubCategories();
    Category GetSubCategoryById(Integer id);
    int DeleteSubCategory(Integer id);
    Category CreateSubCategory(CreateSubCategoryRequest request);
    Category PatchSubCategory(PatchSubCategoryRequest request);
}
