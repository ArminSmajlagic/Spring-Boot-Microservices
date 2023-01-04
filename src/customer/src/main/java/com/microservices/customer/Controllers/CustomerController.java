package com.microservices.customer.Controllers;

import com.microservices.customer.Models.Customer;
import com.microservices.customer.Requests.CreateCustomerRequest;
import com.microservices.customer.Requests.PatchCustomerRequest;
import com.microservices.customer.Services.CustomerService;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @GetMapping
    public ResponseEntity<List<Customer>> GetAll(){
        log.info("Customer controller -> getting all customers");

        var result = customerService.GetCustomers();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getById")
    public ResponseEntity<Customer> GetById(@RequestParam("id") Integer id){

        if(id == 0 || id == null)
            return new ResponseEntity ("You have to pass a valid id", HttpStatus.BAD_REQUEST);

        log.info("Customer controller -> getting by id customer");

        var result = customerService.GetCustomerById(id);

        if(result==null)
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<Customer> CreateCustomer(@RequestBody CreateCustomerRequest request){

        if(request == null)
            return new ResponseEntity ("You have to pass a valid request", HttpStatus.BAD_REQUEST);

        log.info("Customer controller -> creating customer");

        var result = customerService.CreateCustomer(request);


        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<String> Delete(@RequestParam("id") Integer id){

        if(id == 0 || id == null)
            return new ResponseEntity ("You have to pass a valid id", HttpStatus.BAD_REQUEST);

        log.info("Customer controller -> deleting customer");

        var result = customerService.DeleteCustomer(id);

        if(result==0)
            return new ResponseEntity<>("Customer with given id was not found!", HttpStatusCode.valueOf(404));

        return new ResponseEntity<>("Customer was successfully deleted!", HttpStatusCode.valueOf(200));
    }

    @PatchMapping
    public ResponseEntity<String> PatchCustomer(@RequestBody PatchCustomerRequest request){

        if(request == null)
            return new ResponseEntity ("You have to pass a valid request", HttpStatus.BAD_REQUEST);

        log.info("Customer controller -> patching customer");

        var result = customerService.UpdateCustomer(request);

        if(result==null)
            return new ResponseEntity<>("Customer with given id was not found!", HttpStatusCode.valueOf(404));

        return new ResponseEntity<>("Customer was successfully patched!", HttpStatusCode.valueOf(200));
    }
}
