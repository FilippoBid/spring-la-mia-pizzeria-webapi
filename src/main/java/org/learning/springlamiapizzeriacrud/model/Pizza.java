package org.learning.springlamiapizzeriacrud.model;

import jakarta.persistence.*;


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

    @Column(nullable = false)
    private String name;
    private String description;
    private String urlPhoto;
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
