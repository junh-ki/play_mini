package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Apartment {

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String category;

    @Constraints.Required
    private String description;

    @Constraints.Min(10)
    private Integer size;

    @Constraints.Min(5)
    private Double price;

    // Constructors
    public Apartment() {

    }

    public Apartment(Long id, String name, String category, String description, Integer size, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", price=" + price +
                '}';
    }

}
