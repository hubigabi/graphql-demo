package pl.edu.utp.graphqldemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import pl.edu.utp.graphqldemo.model.Customer;
import pl.edu.utp.graphqldemo.model.Order;
import pl.edu.utp.graphqldemo.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @QueryMapping(name = "customers")
    List<Customer> customers() {
        return customerRepository.findAll();
    }

    @SchemaMapping(typeName = "Customer", field = "orders")
    List<Order> orders(Customer customer) {
        ArrayList<Order> orders = new ArrayList<>();
        for (long orderId = 1; orderId <= Math.random() * 10; orderId++) {
            orders.add(new Order(orderId, customer.getId()));
        }
        return orders;
    }

    @QueryMapping(name = "customersByName")
    List<Customer> customersByName(@Argument String name) {
        return customerRepository.findAllByName(name);
    }

    @MutationMapping(name = "addCustomer")
    Customer addCustomer(@Argument String name, @Argument int age) {
        return customerRepository.save(new Customer(0L, name, age));
    }

}
