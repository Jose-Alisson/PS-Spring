package br.com.sunshine.dto;

import br.com.sunshine.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
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
