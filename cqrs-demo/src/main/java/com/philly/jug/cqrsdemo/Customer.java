package com.philly.jug.cqrsdemo;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Customer {
  @AggregateIdentifier
  private String id;

  @CommandHandler
  public Customer(RegisterCustomerCommand command) {
    Assert.hasLength(command.getFirstName(), "Need a first name!");
    Assert.hasLength(command.getLastName(), "Need a last name!");
    apply(new CustomerRegisteredEvent(command.getCustomerId(), command.getFirstName(), command.getLastName()));
  }

  @EventSourcingHandler
  void on(CustomerRegisteredEvent event) {
    this.id = event.getCustomerId();
  }

}
