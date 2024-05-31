package br.com.sapucaians.model;

import br.com.sapucaians.enums.AmountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "amounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Amount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Enumerated(EnumType.STRING)
	private AmountStatus status;

	private int quantity;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Attribute> selectedAttribute;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Column(length = 500)
	private String description;
}
