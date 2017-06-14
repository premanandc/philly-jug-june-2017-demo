package com.philly.jug.cqrsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisteredEvent {
  private String customerId;
  private String firstName;
  private String lastName;

}
