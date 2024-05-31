package br.com.sapucaians.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountDTO {

    @NotBlank(message = "o id não pode ser vazio")
    @NotNull(message = "o id não pode ser nulo")
    Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @NotNull(message = "O nome não pode ser nulo")
    String name;

    @NotBlank(message = "O email não pode ser vazio")
    @NotNull(message = "O email não pode ser nulo")
    String email;

    String photoUrl;

    @NotBlank(message = "o telefone não pode ser vazio")
    @NotNull(message = "o telefone não pode ser nulo")
    String phone;
}
