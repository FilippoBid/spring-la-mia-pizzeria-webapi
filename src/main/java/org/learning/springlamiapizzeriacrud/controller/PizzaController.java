package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Ingredient;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.IngredientRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

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
            return "pizzaDetail.html";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + id + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allIngredients", allIngredients);
        return "createPizza.html";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model,
                        @RequestParam("ingredients") List<Integer> ingredientIds) {
        if (bindingResult.hasErrors()) {
            List<Ingredient> allIngredients = ingredientRepository.findAll();

            model.addAttribute("allIngredients", allIngredients);
            return "createPizza.html";
        }
        List<Ingredient> selectedIngredients = ingredientRepository.findAllById(ingredientIds);
        formPizza.setIngredients(selectedIngredients);
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
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        model.addAttribute("pizza", pizza);
        model.addAttribute("allIngredients", allIngredients);
        return "createPizza.html";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                         @RequestParam("ingredients") List<Integer> ingredientIds) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
        if (bindingResult.hasErrors()) {
            List<Ingredient> allIngredients = ingredientRepository.findAll();
            model.addAttribute("allIngredients", allIngredients);
            return "createPizza.html";
        }
        Pizza pizzaToEdit = pizzaOptional.get();
        List<Ingredient> selectedIngredients = ingredientRepository.findAllById(ingredientIds);
        formPizza.setId(pizzaToEdit.getId());
        formPizza.setIngredients(selectedIngredients);
        pizzaRepository.save(formPizza);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza updated!"));
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
        Pizza pizzaToDelete = pizzaOptional.get();
        pizzaRepository.delete(pizzaToDelete);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Pizza " + pizzaToDelete.getName() + " deleted!"));
        return "redirect:/pizza";
    }
}
