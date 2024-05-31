package br.com.sapucaians.dto;

import br.com.sapucaians.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private AccountDTO account;
    private List<AmountDTO> amounts;
    private OrderStatus status;
    private String dateCreation;
    private AddressDTO address;
    private String typePay;
    private double troco;
    private String tableOrCliente;
}
