package org.learning.springlamiapizzeriacrud.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
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
