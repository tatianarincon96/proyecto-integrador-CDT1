package com.proyecto.integrador.DTO;
import com.proyecto.integrador.persistence.entity.Feature;
import com.proyecto.integrador.persistence.entity.Product;
import com.proyecto.integrador.persistence.entity.enums.FeatureTypes;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class FeatureRequestDTO {
    private Integer id;
    private String title;
    private FeatureTypes type;
    private Set<Product> products = new HashSet<>();

    public FeatureRequestDTO() {
    }

    public FeatureRequestDTO(Integer id, String title, FeatureTypes type, Set<Product> products) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.products = products;
    }

    public FeatureRequestDTO(String title, FeatureTypes type, Set<Product> products) {
        this.title = title;
        this.type = type;
        this.products = products;
    }

    public Feature toEntity() {
        Feature feature = new Feature();
        feature.setTitle(title);
        feature.setType(type);
        feature.setProducts(products);
        return feature;
    }
}

