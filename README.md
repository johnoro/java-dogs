# Project RESTDogs

# Introduction
Develop a simple REST Web application to work with data about dogs
- [x] Dogs have an auto generated id, breed name, average weight, boolean that true if the breed of dog is appropriate for apartment living otherwise false.

# Instructions
Create a Java RESTful API Web application using Web, DevTools, H2, hateoas, and Jackson and JPA.  

## Data
- [x] Seed the data with the following  

```java
new Dogs("Springer", 50, false);
new Dogs("Bulldog", 50, true);
new Dogs("Collie", 50, false);
new Dogs("Boston Terrie", 35, true);
new Dogs("Corgi", 35, true);
```

## Expose the following endpoints

### Get

- [x] /dogs/breeds -> returns a listing of all dogs ordered by breed   
- [x] /dogs/weight -> returns a listing of all dogs ordered by average weight  
- [x] /dogs/breeds/{breed} -> returns dogs of just that breed  
- [x] /dogs/apartment -> returns dogs suitable for apartments    

### Put

- [x] /dogs/{id} -> adds or update if already present the dog with id value id  

### Post  

- [x] /dogs -> adds the dog

### Delete

- [x] /dogs/{id} -> deletes the dog with that id

## Recommended Steps
1. Build a new project . 
2. Add properties (DEBUG logs) and dependencies (Jackson) . 
3. Create the data class . 
4. Create the SeedDatabase class . 
5. Create the repository class . 
6. Create the ResourceAssembler class . 
7. Create the Controller class . 
8. Create the ItemNotFoundException class .  
8. Now you can finally expose your endpoints!

## Stretch goal and not necessary for MVP  

### Delete  

- [x] /dogs/breeds/{breed} -> deletes that dog breed

Throughout the project, when adding dogs make sure the **breed names are unique**
