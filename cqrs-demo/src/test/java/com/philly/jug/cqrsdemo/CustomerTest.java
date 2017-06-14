package com.philly.jug.cqrsdemo;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
  private FixtureConfiguration<Customer> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture<>(Customer.class);
  }

  @Test
  public void shouldNotRegisterCustomerWithABlankFirstName() throws Exception {
    fixture.given()
        .when(new RegisterCustomerCommand("id", "", "last"))
        .expectException(RuntimeException.class);
  }

  @Test
  public void shouldRegisterCustomerWithAValidFirstAndLastName() throws Exception {
    fixture.given()
        .when(new RegisterCustomerCommand("id", "first", "last"))
        .expectEvents(new CustomerRegisteredEvent("id", "first", "las"));
  }
}