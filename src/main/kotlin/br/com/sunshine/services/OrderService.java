package br.com.sunshine.services;

import br.com.sunshine.dto.OrderDTO;
import br.com.sunshine.enums.AmountStatus;
import br.com.sunshine.enums.OrderStatus;
import br.com.sunshine.enums.PaymentStatus;
import br.com.sunshine.exception.causable.ErrDateTransfer;
import br.com.sunshine.model.*;
import br.com.sunshine.repository.AccountRepository;
import br.com.sunshine.repository.AmountRepository;
import br.com.sunshine.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AmountRepository amountRepository;

    private WebClient http = WebClient.create("http://localhost:4444");

    private final ModelMapper mapper = new ModelMapper();

    public OrderDTO create(Order order) {

        order.setStatus(OrderStatus.CRIADO);
        order.setDateCreation("" + LocalDateTime.now());

        if (order.getAccount() == null) {
            var principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = Account.builder().id(accountRepository.getIdByEmail(principal)).build();
            order.setAccount(account);
        }

        var order_ = repository.save(order);

        order.getAmounts().forEach(amount -> {
            Optional<Amount> opt = amountRepository.findById(amount.getId());

            if (opt.isPresent()) {
                Amount amount_ = opt.get();
                amount_.setStatus(AmountStatus.ANEXADO);
                amount = amountRepository.save(amount_);

                System.out.println("Passou: " + amount_.getId());
                System.out.println(amount_);
            }
        });

        return mapper.map(order_, OrderDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public OrderDTO getById(Long id) {
        Optional<Order> opt = repository.findById(id);

        if (opt.isPresent()) {
            return mapper.map(opt.get(), OrderDTO.class);
        }
        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }
    public List<OrderDTO> getAllByUserId(Long id) {
        return repository.findAllByUserId(id).stream().map(order -> mapper.map(order, OrderDTO.class)).toList();
    }

    public OrderDTO update(OrderDTO orderDto) {
        Optional<Order> order_ = repository.findById(orderDto.getId());

        if (order_.isPresent()) {
            Order order = order_.get();
            order.setAmounts(orderDto.getAmounts().stream().map(amount -> mapper.map(amount, Amount.class)).toList());
            order.setStatus(orderDto.getStatus());

            if (order.getAccount() == null) {
                var principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Account account = Account.builder().id(accountRepository.getIdByEmail(principal)).build();
                order.setAccount(account);
            }

            order.setAddress(mapper.map(orderDto.getAddress(), Address.class));

            return mapper.map(repository.save(order), OrderDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }
    public OrderDTO addAmount(Long id, Amount amount) {
        Optional<Order> order_ = repository.findById(id);

        if (order_.isPresent()) {
            Order order = order_.get();
            order.getAmounts().add(amount);

            return mapper.map(order, OrderDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }
    public OrderDTO removeAmount(Long id, String amountId) {
        Optional<Order> order_ = repository.findById(id);

        if (order_.isPresent()) {
            Order order = order_.get();
            order.setAmounts(order.getAmounts().stream().filter(amount -> !amount.getId().equals(amountId)).toList());

            return mapper.map(repository.save(order), OrderDTO.class);
        }

        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }
    public List<OrderDTO> getByLogged() {

        var principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = Account.builder().id(accountRepository.getIdByEmail(principal)).build();

        if (account.getId() != 0){
            return repository.findAllByUserId(account.getId()).stream().map(order -> mapper.map(order, OrderDTO.class)).toList();
        }
        throw new ErrDateTransfer("", HttpStatus.NOT_FOUND);
    }

    public void wsAddToList(OrderDTO orderDTO){
        http.post()
                .uri("http://localhost:4444/addToList")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(orderDTO))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Object loadAllToList() {
        var orders = repository.findAll().stream().map(order -> mapper.map(order, OrderDTO.class)).toList();

        System.out.println(orders);

//        var map = new HashMap<>();
//        map.put("orders", orders);

        String mono = http.post()
                .uri("/loadToList")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(orders))
                .retrieve()
                .bodyToMono(String.class).block();

        //mono.subscribe(System.out::println);

        return mono;
    }
}
