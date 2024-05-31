package br.com.sapucaians.dto;

import lombok.Data;

import java.util.List;

@Data
public class AmountDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private List<AttributeDTO> selectedAttribute;
    private String description;
}
