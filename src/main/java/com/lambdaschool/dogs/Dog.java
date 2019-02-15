package com.lambdaschool.dogs;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data // getters, setters, toString
@Entity // ready for JPA storage
public class Dog {
  private @Id @GeneratedValue Long id;
  private String breed;
  private double averageWeight;
  private boolean isGoodForApartment;

  public Dog() {}

  public Dog(String breed, double averageWeight, boolean isGoodForApartment) {
    this.breed = breed;
    this.averageWeight = averageWeight;
    this.isGoodForApartment = isGoodForApartment;
  }
}
