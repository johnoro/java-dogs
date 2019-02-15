package com.lambdaschool.dogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("dogs")
public class DogController {
  private final DogRepository repository;
  private final DogResourceAssembler assembler;

  public DogController(DogRepository repository, DogResourceAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("")
  public Resources<Resource<Dog>> all() {
    List<Resource<Dog>> dogs = repository.findAll().stream()
      .map(assembler::toResource)
      .collect(Collectors.toList());

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).all()).withSelfRel()
    );
  }

  @GetMapping("/{id}")
  public Resource<Dog> find(@PathVariable Long id) {
    Dog foundDog = repository.findById(id)
      .orElseThrow(() -> new DogNotFoundException(id));

    return assembler.toResource(foundDog);
  }

  @GetMapping("/breeds")
  public Resources<Resource<Dog>> allByBreed() {
    List<Resource<Dog>> dogs = repository.findAll().stream()
      .sorted((d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed()))
      .map(assembler::toResource)
      .collect(Collectors.toList());

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).allByBreed()).withSelfRel()
    );
  }
}
