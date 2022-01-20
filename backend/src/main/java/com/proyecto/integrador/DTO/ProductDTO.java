package com.proyecto.integrador.DTO;
import com.proyecto.integrador.persistence.entity.Category;
import com.proyecto.integrador.persistence.entity.City;
import com.proyecto.integrador.persistence.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String address;
    private double qualification;
    private String reference;
    private CategoryDTO category;
    private CityDTO city;
    private String rules;
    private String health;
    private String politics;
    private boolean deleted;
    private Set<ImageDTO> images = new HashSet<>();
    private Set<FeatureResponseDTO> features = new HashSet<>();

    public ProductDTO() {
    }

    public ProductDTO(Integer id, double qualification) {
        this.id = id;
        this.qualification = qualification;
    }

    public ProductDTO(Integer id) {
        this.id = id;
    }

    public ProductDTO(String name, String description, double latitude, double longitude, String address, double qualification, String reference, CategoryDTO category, CityDTO city, String rules, String health, String politics) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.qualification = qualification;
        this.reference = reference;
        this.category = category;
        this.city = city;
        this.rules = rules;
        this.health = health;
        this.politics = politics;
        this.deleted = false;
    }

    public ProductDTO(Integer id, String name, String description, double latitude, double longitude, String address, double qualification, String reference, CategoryDTO category, CityDTO city, String rules, String health, String politics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.qualification = qualification;
        this.reference = reference;
        this.category = category;
        this.city = city;
        this.rules = rules;
        this.health = health;
        this.politics = politics;
        this.deleted = false;
    }

    public Product toEntity(){
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setLatitude(latitude);
        product.setLongitude(longitude);
        product.setAddress(address);
        product.setQualification(5);
        product.setReference(reference);
        product.setCategory(new Category(category.getId()));
        product.setCity(new City(city.getId()));
        product.setRules(rules);
        product.setHealth(health);
        product.setPolitics(politics);
        product.setDeleted(deleted);
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO that = (ProductDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
