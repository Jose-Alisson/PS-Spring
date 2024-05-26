package br.com.sunshine.model;

import br.com.sunshine.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "order_amounts", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "amount_id"))
	private List<Amount> amounts;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "date_creation")
	private String dateCreation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;

	private String typePay;

	private double troco;

	private String tableOrCliente;
}
