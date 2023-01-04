
package com.microservices.products.Services;

import com.microservices.products.DAOs.ProductDaoImpl;
import com.microservices.products.Models.Product;
import com.microservices.products.Requests.CreateProductRequest;
import com.microservices.products.Requests.PatchProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductDaoImpl productDao;

    public List<Product> GetProducts(){
        var result = productDao.GetAll();

        log.info("Product service -> getting all products");

        return  result;
    }

    @Override
    public Product GetProductById(Integer id) {
        var result = productDao.GetById(id);

        log.info("Product service -> getting product by id");

        return  result;
    }

    @Override
    public int DeleteProduct(Integer id) {
        var result = productDao.Delete(id);

        log.info("Product service -> getting all products");

        return  result;
    }

    public Product CreateProduct(CreateProductRequest request){

      Product product = Product
              .builder()
              .description(request.description())
              .price(request.price())
              .name(request.name())
              .id(request.id())
              .subCategoryId(request.subCategoryId())
              .build();

      var result = productDao.Save(product);

      log.info("Product service -> Product created");

      return result;
    }

    @Override
    public Product PatchProduct(PatchProductRequest request) {
        Product product = Product
                .builder()
                .description(request.description())
                .price(request.price())
                .name(request.name())
                .id(request.id())
                .subCategoryId(request.subCategoryId())
                .build();

        var result = productDao.Patch(product);

        log.info("Product service -> Product patched");

        return result;
    }
}

