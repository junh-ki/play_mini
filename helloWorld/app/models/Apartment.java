package models;

public class Apartment {

    // properties
    private Long id;
    private String name;
    private String category;
    private String description;
    private Integer size;
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

}
