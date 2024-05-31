package br.com.sapucaians.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    private String complement;

    @Column(name = "point_reference")
    private String pointReference;

    @NotBlank(message = "O cep não pode ser vazio")
    @NotNull(message = "O cep não pode ser nulo")
    private String zipCode;

    private String log;

    private String lat;
}
