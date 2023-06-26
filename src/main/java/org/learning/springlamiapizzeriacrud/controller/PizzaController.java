package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(Model model) {

        List<Pizza> pizzas = pizzaRepository.findAll();

        model.addAttribute("menu", pizzas);

        return "indexPizze.html";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()) {
            Pizza pizza = pizzaOptional.get();

            model.addAttribute("pizzadettaglio", pizza);
            // ritorna il nome del template della view
            return "pizzaDetail.html";
        } else {
            // ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with " + id + " not found");
        }


    }

    @GetMapping("/create")
    public String create(Model model) {
        // aggiungo al model l'attributo book contenente un Book vuoto
        model.addAttribute("pizza", new Pizza());
        return "createPizza.html";
    }

    @PostMapping("/create")
    public String store(@Validated @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {


        // verifico se in validazione ci sono stati errori
        if (bindingResult.hasErrors()) {
            // ci sono stati errori
            return "createPizza.html"; // ritorno il template del form ma col pizza
        }


        // il metodo save fa una create sql se l'oggetto con quella PK non esiste, altrimenti fa update
        pizzaRepository.save(formPizza);


        return "redirect:/indexPizze.html";
    }
}
