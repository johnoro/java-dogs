package com.lambdaschool.dogs;

public class DogNotFoundException extends RuntimeException {
  public DogNotFoundException(Long id) {
    super("Could not find dog " + id);
  }
}
