package com.proyecto.integrador.persistence.entity;

import com.proyecto.integrador.DTO.CategoryDTO;
import com.proyecto.integrador.DTO.CityDTO;
import com.proyecto.integrador.DTO.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name ="product")
@Where(clause = "deleted = 0")
public class Product {
    @Id
    @Column(name="idProduct", nullable = false)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name="description", nullable = false, length = 500)
    private String description;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "qualification", nullable = false)
    private double qualification;
    @Column(name = "reference", nullable = false)
    private String reference;
    @Column(name = "rules", nullable = false)
    private String rules;
    @Column(name = "health", nullable = false)
    private String health;
    @Column(name = "politics", nullable = false)
    private String politics;
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
    @ManyToOne
    @JoinColumn(name = "idCategory", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "idCity", nullable = false)
    private City city;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private  Set<Score> scores = new HashSet<>();
    @ManyToMany(targetEntity = Feature.class, mappedBy = "products", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Feature> features = new ArrayList<>();
    @ManyToMany(mappedBy = "favoriteProducts", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<Reservation> reservations= new HashSet<>();

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String name, String description, double latitude, double longitude, String address, double qualification, String reference, Category category, City city, String rules, String health, String politics, boolean deleted) {
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
        this.deleted=deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.getLatitude(), getLatitude()) == 0 && Double.compare(product.getLongitude(), getLongitude()) == 0 && getName().equals(product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLatitude(), getLongitude());
    }


/*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId().equals(product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }*/

    public ProductDTO toDto(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setLatitude(latitude);
        productDTO.setLongitude(longitude);
        productDTO.setAddress(address);
        productDTO.setReference(reference);
        productDTO.setCategory(new CategoryDTO(category.getId()));
        productDTO.setCity(new CityDTO(city.getId()));
        productDTO.setRules(rules);
        productDTO.setHealth(health);
        productDTO.setPolitics(politics);
        productDTO.setDeleted(deleted);
        return productDTO;
    }
}
