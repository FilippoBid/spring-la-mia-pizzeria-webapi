package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

        model.addAttribute("pizza", new Pizza());
        return "createPizza.html";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {


        // verifico se in validazione ci sono stati errori
        if (bindingResult.hasErrors()) {

            return "/pizza/create";
        }


        pizzaRepository.save(formPizza);


        return "redirect:/pizza";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
        Pizza pizza = pizzaOptional.get();
        model.addAttribute("pizza", pizza);
        return "editPizza.html";
    }
/*
    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        Pizza pizzaToEdit = pizzaRepository.findById(id);

        if (bindingResult.hasErrors()) {
            return "/editPizza.html";
        }

        // Update the properties of the pizza
        formPizza.setName(pizzaToEdit.getName());
        formPizza.setPrice(pizzaToEdit.getPrice());
        // Update other properties as needed
        pizzaRepository.save(formPizza);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza updated!"));
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Pizza> pizzaToDelete = pizzaRepository.findById(id);
        pizzaRepository.delete(pizzaToDelete);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza " + pizzaToDelete.getName() + " deleted!"));
        return "redirect:/pizza";
    }*/
}
