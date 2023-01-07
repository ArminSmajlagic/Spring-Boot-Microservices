package com.microservices.products.Controllers;

import com.microservices.products.Services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("subCategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService service;

    public SubCategoryController(){}
}
