package com.microservices.customer.Requests;

public record CreateCustomerRequest(String full_name, String username, String password, String address) {
}
