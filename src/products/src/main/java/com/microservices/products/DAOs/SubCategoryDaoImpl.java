package com.microservices.products.DAOs;

import com.microservices.products.Models.Product;
import com.microservices.products.Models.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class SubCategoryDaoImpl implements SubCategoryDao{

    private static final String HASH_KEY = "SubCategory";
    @Autowired
    private RedisTemplate<String, Object> template;
    @Override
    public List<SubCategory> GetAll() {
        var result = template.opsForHash().values(HASH_KEY);
        return (List<SubCategory>)(Object)result;
    }

    @Override
    public SubCategory GetById(Integer id) {
        return (SubCategory)template.opsForHash().get(HASH_KEY, id.toString());
    }

    @Override
    public SubCategory Patch(SubCategory product) {
        if(GetById(product.getId()) == null)
            return null;

        template.opsForHash().put(HASH_KEY, product.getId().toString(), product);

        return product;
    }

    @Override
    public SubCategory Save(SubCategory prod) {
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
        }    }
}
