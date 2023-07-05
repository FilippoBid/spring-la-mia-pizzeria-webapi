package org.learning.springlamiapizzeriacrud.api;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizza")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> index() {
        return pizzaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pizza get(@PathVariable Integer id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found"));
    }

    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        if (pizzaRepository.existsById(id)) {
            pizzaRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");
        }
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        if (pizzaRepository.existsById(id)) {
            pizza.setId(id);
            return pizzaRepository.save(pizza);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");
        }
    }

    @GetMapping("/page")
    public Page<Pizza> page(Pageable pageable) {
        return pizzaRepository.findAll(pageable);
    }
}
