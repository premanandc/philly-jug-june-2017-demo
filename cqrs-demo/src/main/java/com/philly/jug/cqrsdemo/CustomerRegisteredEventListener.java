package com.philly.jug.cqrsdemo;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegisteredEventListener {
  private final CustomerViewRepository repository;

  public CustomerRegisteredEventListener(CustomerViewRepository repository) {
    this.repository = repository;
  }

  @EventHandler
  public void on(CustomerRegisteredEvent event) {
    repository.save(new CustomerView(event.getCustomerId(), event.getFirstName(), event.getLastName()));
  }
}
