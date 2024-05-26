package br.com.sunshine.dto;

import br.com.sunshine.model.Attribute;
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
