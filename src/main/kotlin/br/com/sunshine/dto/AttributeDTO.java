package br.com.sunshine.dto;

import lombok.Data;

@Data
public class AttributeDTO {
    private Long id;
    private String photoUrl;
    private String attributeName;
    private double attributePrice;
    private int available;
}
