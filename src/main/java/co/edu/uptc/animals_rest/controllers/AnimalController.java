package co.edu.uptc.animals_rest.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.animals_rest.models.Animal;
import co.edu.uptc.animals_rest.services.AnimalService;




@RestController
@RequestMapping("/animal")
public class AnimalController {

 private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

   @Autowired
    private AnimalService animalService;


    @GetMapping("/all")
    public List<Animal> getAnimalAll() throws IOException {
        logger.info("getAnimalAll called");
        return animalService.getAnimalAll();
    }

    @GetMapping("/range")
    public List<Animal> getAnimal(@RequestParam int from, @RequestParam int to) throws IOException {
        logger.info("getAnimal called with parameters: from = {}, to = {}", from, to);
        return animalService.getAnimalInRange(from, to);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAnimalsByCategory(@PathVariable String category) throws IOException {
        logger.info("getAnimalsByCategory called for category = {}", category);
    
        List<Animal> animals = animalService.getAnimalsByCategory(category);
    
        if (animals.isEmpty()) {
            logger.warn("Category not found: {}", category);
            return new ResponseEntity<>("Category '" + category + "' not found or has no animals.", HttpStatus.NOT_FOUND);
        }
    
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }


    @GetMapping("/name-length/{numberOfLetters}")
    public ResponseEntity<?> getAnimalsByNameLength(@PathVariable int numberOfLetters) throws IOException {
        logger.info("getAnimalsByNameLength called for numberOfLetters = {}", numberOfLetters);
        
        List<Animal> animals = animalService.getAnimalsByNameLength(numberOfLetters);
        
        if (animals.isEmpty()) {
            logger.warn("No animals found with names shorter than {} characters", numberOfLetters);
            return new ResponseEntity<>("No animals found with names shorter than " + numberOfLetters + " characters.", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }
    

}
