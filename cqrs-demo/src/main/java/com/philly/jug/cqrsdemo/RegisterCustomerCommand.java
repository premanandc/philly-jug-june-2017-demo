package com.philly.jug.cqrsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerCommand {
  private String customerId;
  private String firstName;
  private String lastName;

}
