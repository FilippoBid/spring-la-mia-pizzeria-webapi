package org.learning.springlamiapizzeriacrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank(message = "riempi i campi mancanti")
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pizza pizza;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
