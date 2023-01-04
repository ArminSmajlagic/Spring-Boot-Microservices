package com.microservices.products.DAOs;

import com.microservices.products.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    private static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate<String, Object> template;
    @Override
    public List<Product> GetAll() {
        var result = template.opsForHash().values(HASH_KEY);
        return (List<Product>)(Object)result;
    }
    @Override
    public Product GetById(Integer id) {
        return (Product)template.opsForHash().get(HASH_KEY, id.toString());
    }

    @Override
    public Product Patch(Product product) {

        if(GetById(product.getId()) == null)
            return null;

        template.opsForHash().put(HASH_KEY, product.getId().toString(), product);

        return product;
    }
    @Override
    public Product Save(Product prod) {
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
