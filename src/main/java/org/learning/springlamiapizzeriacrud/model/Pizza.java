package org.learning.springlamiapizzeriacrud.model;

import jakarta.persistence.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    /*@NotBlank(message = "Title must not be null or blank")*/
    @Column(nullable = false)
    @NotNull
    @NotBlank(message = "riempi i campi mancanti")
    private String name;
    private String description;
    private String urlPhoto;

    @DecimalMin("0.01")
    private BigDecimal price;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    /*     il riferimento è fatto allinterno del file Offer model e deve avere lo stesso nome dichiarato li */
    @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})

    private List<Offer> offers;


    public Pizza() {

    }

    /*  public Pizza(String name, String description, String urlPhoto, BigDecimal price) {
              this.name = name;
              this.description = description;
              this.urlPhoto = urlPhoto;
              this.price = price;
          }
      */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
