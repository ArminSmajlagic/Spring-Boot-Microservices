package com.microservices.customer.Requests;

public record PatchCustomerRequest(Integer id, String full_name, String username, String address){
}
