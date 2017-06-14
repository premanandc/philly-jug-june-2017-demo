package com.philly.jug.cqrsdemo;

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

@SpringBootApplication
public class CqrsDemoApplication {

  private final AmqpAdmin admin;

  public CqrsDemoApplication(AmqpAdmin admin) {
    this.admin = admin;
  }

  public static void main(String[] args) {
    SpringApplication.run(CqrsDemoApplication.class, args);
  }

  @Bean
  public Exchange exchange(@Value("${axon.amqp.exchange}") String name) {
    Exchange exchange = ExchangeBuilder.topicExchange(name).durable(true).build();
    this.admin.declareExchange(exchange);
    return exchange;
  }

  @Bean
  public Queue queue() {
    Queue queue = QueueBuilder.durable("CustomersQueue").build();
    this.admin.declareQueue(queue);
    return queue;
  }

  @Bean
  public Binding binding(Exchange exchange, Queue queue) {
    Binding binding = BindingBuilder.bind(queue).to(exchange).with("#").noargs();
    this.admin.declareBinding(binding);
    return binding;
  }
}
