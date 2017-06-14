package com.philly.jug.cqrsdemo;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerViewRepository repository;
  private final CommandGateway commandGateway;

  public CustomerController(CustomerViewRepository repository, CommandGateway commandGateway) {
    this.repository = repository;
    this.commandGateway = commandGateway;
  }

  @PostMapping
  public CompletableFuture<String> create(@RequestBody Map<String, String> request) {
    return commandGateway.send(new RegisterCustomerCommand(UUID.randomUUID().toString(),
        request.get("firstName"), request.get("lastName")));
  }

  @GetMapping
  public Iterable<CustomerView> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public CustomerView findOne(@PathVariable String id) {
    return repository.findOne(id);
  }
}
