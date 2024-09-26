package co.edu.uptc.animals_rest.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/numberByCategory")
    public ResponseEntity<List<Map<String, Object>>> getNumberByCategory() throws IOException {
        logger.info("getNumberByCategory called");

        // Llamada al servicio para obtener el mapa de categorías y cantidades
        Map<String, Long> animalsByCategory = animalService.getNumberOfAnimalsByCategory();

    // Convertir el mapa a una lista de mapas con el formato requerido
        List<Map<String, Object>> result = animalsByCategory.entrySet().stream()
            .map(entry -> {
                Map<String, Object> categoryInfo = new HashMap<>();
                categoryInfo.put("category", entry.getKey());
                categoryInfo.put("number", entry.getValue());
                return categoryInfo;
            })
            .collect(Collectors.toList());

    return new ResponseEntity<>(result, HttpStatus.OK);
}

}
