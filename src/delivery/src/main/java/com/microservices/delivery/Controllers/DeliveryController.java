package com.microservices.delivery.Controllers;

import com.microservices.delivery.Models.Delivery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("delivery")
public class DeliveryController {

    @GetMapping
    public List<Delivery> GetAll(){
        List<Delivery> result = null;

        return result;
    }
}
