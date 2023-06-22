package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(Model model) { // Model è la mappa di attributi che il controller passa alla view
        // recupero la lista di libri dal database

        List<Pizza> pizzas = pizzaRepository.findAll();
        // passo la lista dei libri alla view
        model.addAttribute("menu", pizzas);
        // restituisco il nome del template della view
        return "indexPizze.html";

    }
}
