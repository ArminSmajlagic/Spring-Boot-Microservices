package com.microservices.customer.Services;

import com.microservices.customer.Models.Customer;
import com.microservices.customer.Repositories.ICustomerRepository;
import com.microservices.customer.Requests.CreateCustomerRequest;
import com.microservices.customer.Requests.PatchCustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository repository;

    public List<Customer> GetCustomers(){
        var result = repository.findAll();

        log.info("Customer service -> getting all Customers");

        return  result;
    }

    public Customer GetCustomerById(Integer id){

        try {
            var result = repository.findById(id);

            log.info("Customer service -> getting customer by id");

            return result.get();

        }catch (Exception ex){
            return null;
        }
    }

    public int DeleteCustomer(Integer id){

        if(GetCustomerById(id)==null)
            return 0;

        repository.deleteById(id);

        log.info("Customer service -> deleting customer");

        return  1;
    }

    public Customer UpdateCustomer(PatchCustomerRequest request){

        try {
            var customer = repository.findById(request.id());

            if(!request.username().isBlank() && !request.username().isEmpty())
                customer.get().setUsername(request.username());
            if(!request.address().isBlank() && !request.address().isEmpty())
                customer.get().setAddress(request.address());
            if(!request.full_name().isBlank() && !request.full_name().isEmpty())
                customer.get().setFull_name(request.full_name());

            var result = repository.save(customer.get());

            log.info("Customer service -> patching customer");

            return  result;

        }catch (Exception ex){
            return null;
        }


    }
    public Customer CreateCustomer(CreateCustomerRequest request){

        Customer product = Customer
                .builder()
                .password(request.password())
                .full_name(request.full_name())
                .username(request.username())
                .address(request.address())
                .id(0)
                .build();

        var result = repository.save(product);

        log.info("Customer service -> Customer created");

        return result;
    }
}
