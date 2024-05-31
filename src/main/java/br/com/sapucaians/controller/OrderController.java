package br.com.sapucaians.controller;

import br.com.sapucaians.dto.OrderDTO;
import br.com.sapucaians.model.Amount;
import br.com.sapucaians.model.Order;
import br.com.sapucaians.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid Order order){
        var order_ = service.create(order);
        service.wsAddToList(order_);
        return ResponseEntity.ok(order_);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDTO> update(@RequestBody @Valid OrderDTO order){
        return ResponseEntity.ok(service.update(order));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/byUserId/{id}")
    public ResponseEntity<List<OrderDTO>> getAllByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getAllByUserId(id));
    }

    @PatchMapping("/{id}/addAmount")
    public ResponseEntity<OrderDTO> addAmount(@PathVariable("id") Long id, @RequestBody Amount amount){
        return ResponseEntity.ok(service.addAmount(id, amount));
    }

    @PatchMapping("/{id}/removeAmount/{amountId}")
    public ResponseEntity<?> removeAmount(@PathVariable("id") Long id, @PathVariable("amountId") String amountId){
        return ResponseEntity.ok(service.removeAmount(id, amountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/byLogged")
    public ResponseEntity <?> getByLogged(){
        return ResponseEntity.ok(service.getByLogged());
    }

    @PostMapping("/load")
    public ResponseEntity <?> loadAllToList(){
        return ResponseEntity.ok(service.loadAllToList());
    }
}
