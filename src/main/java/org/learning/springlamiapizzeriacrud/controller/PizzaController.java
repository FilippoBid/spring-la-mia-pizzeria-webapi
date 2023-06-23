package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/pizza/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Object pizza = pizzaRepository.findById(id);

        model.addAttribute("pizza", pizza);

        return "detailPizza.html";

    }
}
