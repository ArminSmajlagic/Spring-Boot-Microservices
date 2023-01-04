package com.microservices.products.DAOs;

import com.microservices.products.Models.Product;

import java.util.List;

public interface ProductDao {
    List<Product> GetAll();
    Product GetById(Integer id);
    Product Patch(Product product);
    Product Save(Product prod);
    int Delete(Integer id);
}
