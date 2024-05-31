package br.com.sapucaians.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    private String street;
    private String houseNumber;
    private String complement;
    private String pointReference;
    private String zipCode;
    private String log;
    private String lat;
}
