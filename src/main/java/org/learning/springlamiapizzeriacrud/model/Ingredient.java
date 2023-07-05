package org.learning.springlamiapizzeriacrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    @Size(min = 1, message = "nome troppo corto")
    private String name;

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients")
    private List<Pizza> pizzas = new ArrayList<>();

    public Ingredient() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

