package com.philly.jug.cqrsdemo;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class CqrsDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(CqrsDemoApplication.class, args);
  }

  @Bean
  public Exchange exchange(@Value("${axon.amqp.exchange}") String name, AmqpAdmin admin) {
    Exchange exchange = ExchangeBuilder.topicExchange(name).durable(true).build();
    admin.declareExchange(exchange);
    return exchange;
  }

  @Bean
  public Queue queue(AmqpAdmin admin) {
    Queue queue = QueueBuilder.durable("CustomersQueue").build();
    admin.declareQueue(queue);
    return queue;
  }

  @Bean
  public Binding binding(Exchange exchange, Queue queue, AmqpAdmin admin) {
    Binding binding = BindingBuilder.bind(queue).to(exchange).with("#").noargs();
    admin.declareBinding(binding);
    return binding;
  }

  @Component
  public static class CustomerRegisteredEventListener {
    private final CustomerViewRepository repository;

    public CustomerRegisteredEventListener(CustomerViewRepository repository) {
      this.repository = repository;
    }

    @EventHandler
    public void on(CustomerRegisteredEvent event) {
      repository.save(new CustomerView(event.getCustomerId(), event.getFirstName(), event.getLastName()));
    }
  }

  @RestController
  @RequestMapping("/customers")
  public static class CustomerController {
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
}
