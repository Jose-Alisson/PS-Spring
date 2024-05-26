package br.com.sunshine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String photoUrl;

	@NotBlank(message = "O nome não pode ser vazio")
	@NotNull(message = "O nome não pode ser nulo")
	private String name;
	
	@Column(unique = true)
	@NotBlank(message = "O email não pode ser vazio")
	@NotNull(message = "O email não pode ser nulo")
	private String email;

	@NotBlank(message = "A senha não pode ser vazia")
	@NotNull(message = "A senha não pode ser nula")
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String password;

	@NotBlank(message = "o telefone não pode ser vazio")
	@NotNull(message = "o telefone não pode ser nulo")
	private String phone;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "account_addresses", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
	private List<Address> addresses = new ArrayList<>();
}
