package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
            // passa il libro alla view
            model.addAttribute("pizzadettaglio", pizza);
            // ritorna il nome del template della view
            return "pizzaDetail.html";
        } else {
            // ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book with id " + id + " not found");
        }


    }
}
