package com.lambdaschool.dogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class DogResourceAssembler implements ResourceAssembler<Dog, Resource<Dog>> {
  @Override
  public Resource<Dog> toResource(Dog dog) {
    return new Resource<>(
      dog
    );
  }
}
