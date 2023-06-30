package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Offer;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.OfferRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    OfferRepository offerRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        // Create a new offer
        Offer offer = new Offer();
        // Preload the start date with the current date
        offer.setStartDate(null);
        // Preload the pizza with the one associated with the provided ID
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
        offer.setPizza(pizza.get());
        // Add the offer to the model
        model.addAttribute("offer", offer);
        return "OfferForm.html";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offer") Offer formOffer,
                           BindingResult bindingResult) {
        // Validate the offer
        if (bindingResult.hasErrors()) {
            // If there are errors, recreate the form template
            return "/offers/form";
        }
        // If there are no errors, save the offer
        offerRepository.save(formOffer);
        // Redirect to the list of offers or the pizza's detail page
        return "redirect:/pizza/" + formOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // Find the offer in the database
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Pass the offer to the model
        model.addAttribute("offer", offer.get());
        return "OfferForm.html";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("offer") Offer formOffer,
                         BindingResult bindingResult) {
        // Check if the offer to edit exists
        Optional<Offer> offerToEdit = offerRepository.findById(id);
        if (offerToEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Set the ID of the offer to the formOffer
        formOffer.setId(id);
        // Save the formOffer to the database (UPDATE)
        offerRepository.save(formOffer);
        // Redirect to the list of offers or the pizza's detail page
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Check if the offer exists
        Optional<Offer> offerToDelete = offerRepository.findById(id);
        if (offerToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // If it exists, delete it
        offerRepository.delete(offerToDelete.get());
        // Redirect to the list of offers or the pizza's detail page
        return "redirect:/pizza/" + offerToDelete.get().getPizza().getId();
    }
}


