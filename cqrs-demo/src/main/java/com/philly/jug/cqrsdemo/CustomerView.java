package com.philly.jug.cqrsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerView {

  @Id
  private String id;

  private String firstName;
  private String lastName;
}
