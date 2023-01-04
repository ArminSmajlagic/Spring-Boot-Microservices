package com.microservices.products.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("SubCategory")
public class SubCategory implements Serializable {
    private Integer id;
    private String name;
    private Integer categoryId;
}
