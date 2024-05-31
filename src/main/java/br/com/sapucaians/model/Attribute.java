package br.com.sapucaians.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_attribute")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "photo_url")
	private String photoUrl;
	
	@Column(name = "attribute_name")
	private String attributeName;

	@Column(name = "attribute_price")
	private double attributePrice;

	@Column(name = "available")
	private int available;
}
