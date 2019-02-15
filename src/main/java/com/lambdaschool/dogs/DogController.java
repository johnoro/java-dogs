package com.lambdaschool.dogs;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogController {
  private final DogRepository repository;
  private final DogResourceAssembler assembler;

  public DogController(DogRepository repository, DogResourceAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }
}
