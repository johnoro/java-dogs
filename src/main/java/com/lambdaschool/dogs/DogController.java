package com.lambdaschool.dogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("dogs")
public class DogController {
  private final DogRepository repository;
  private final DogResourceAssembler assembler;

  private List<Resource<Dog>> getAllBy() {
    return repository.findAll().stream()
      .map(assembler::toResource)
      .collect(Collectors.toList());
  }

  private List<Resource<Dog>> getAllBy(DogSortTester tester) {
    return repository.findAll().stream()
      .sorted(tester::test)
      .map(assembler::toResource)
      .collect(Collectors.toList());
  }

  private List<Resource<Dog>> getSomeBy(DogTester tester) {
    return repository.findAll().stream()
      .filter(tester::test)
      .map(assembler::toResource)
      .collect(Collectors.toList());
  }


  public DogController(DogRepository repository, DogResourceAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("")
  public Resources<Resource<Dog>> all() {
    List<Resource<Dog>> dogs = getAllBy();

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
    List<Resource<Dog>> dogs = getAllBy(
      (d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed())
    );

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).allByBreed()).withSelfRel()
    );
  }

  @GetMapping("/weight")
  public Resources<Resource<Dog>> allByWeight() {
    List<Resource<Dog>> dogs = getAllBy(
      (d1, d2) -> (int)(d1.getAverageWeight() - d2.getAverageWeight())
    );

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).allByBreed()).withSelfRel()
    );
  }

  @GetMapping("/breeds/{breed}")
  public Resources<Resource<Dog>> someByBreed(@PathVariable String breed) {
    List<Resource<Dog>> dogs = getSomeBy(
      dog -> dog.getBreed().equals(breed)
    );

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).someByBreed(breed)).withSelfRel()
    );
  }

  @GetMapping("/apartment")
  public Resources<Resource<Dog>> someByGoodForApt() {
    List<Resource<Dog>> dogs = getSomeBy(
      dog -> dog.isGoodForApartment()
    );

    return new Resources<>(
      dogs,
      linkTo(methodOn(DogController.class).someByGoodForApt()).withSelfRel()
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> replaceDog(@RequestBody Dog newDog, @PathVariable Long id)
    throws URISyntaxException
  {
    Dog updatedDog = repository.findById(id)
      .map(dog -> {
        dog.setAverageWeight(newDog.getAverageWeight());
        dog.setBreed(newDog.getBreed());
        dog.setGoodForApartment(newDog.isGoodForApartment());
        return repository.save(dog);
      })
      .orElseGet(() -> {
        newDog.setId(id);
        return repository.save(newDog);
      });

    Resource<Dog> resource = assembler.toResource(updatedDog);

    return ResponseEntity
      .created(new URI(resource.getId().expand().getHref()))
      .body(resource);
  }

  @PostMapping("")
  public ResponseEntity<?> addDog(@RequestBody Dog newDog)
    throws URISyntaxException
  {
    Dog dog = repository.save(newDog);

    Resource<Dog> resource = assembler.toResource(dog);

    return ResponseEntity
      .created(new URI(resource.getId().expand().getHref()))
      .body(resource);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDog(@PathVariable Long id) {
    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/breeds/{breed}")
  public ResponseEntity<?> deleteDogsByBreed(@PathVariable String breed) {
    repository.findAll().stream()
      .filter(dog -> dog.getBreed().equals(breed))
      .forEach(dog -> repository.delete(dog));

    return ResponseEntity.noContent().build();
  }
}
