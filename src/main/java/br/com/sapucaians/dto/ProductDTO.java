package br.com.sapucaians.dto;

import br.com.sapucaians.model.Attribute;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    Long id;
    String photoUrl;
    String productName;
    String description;
    double basePrice;
    String category;
    int available;
    List<Attribute> attributes;
}
