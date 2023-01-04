package com.microservices.products.DAOs;

import com.microservices.products.Models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao{
    private static final String HASH_KEY = "Category";
    @Autowired
    private RedisTemplate<String, Object> template;
    @Override
    public List<Category> GetAll() {
        var result = template.opsForHash().values(HASH_KEY);
        return (List<Category>)(Object)result;
    }
    @Override
    public Category GetById(Integer id) {
        return (Category)template.opsForHash().get(HASH_KEY, id.toString());
    }

    @Override
    public Category Patch(Category product) {

        if(GetById(product.getId()) == null)
            return null;

        template.opsForHash().put(HASH_KEY, product.getId().toString(), product);

        return product;
    }
    @Override
    public Category Save(Category prod) {
        template.opsForHash().put(HASH_KEY, prod.getId().toString(), prod);

        return prod;
    }

    @Override
    public int Delete(Integer id) {
        try {
            Long result = template.opsForHash().delete(HASH_KEY, id.toString());
            return 1;
        }catch (Exception ex) {
            return 0;
        }
    }
}
