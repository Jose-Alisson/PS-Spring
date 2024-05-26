package br.com.sunshine.controller;

import br.com.sunshine.dto.AddressDTO;
import br.com.sunshine.model.Address;
import br.com.sunshine.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping("/create")
    public ResponseEntity<AddressDTO> create(@RequestBody @Valid Address address){
        return ResponseEntity.ok(service.create(address));
    }

    @PostMapping("/create/byLogged")
    public ResponseEntity<List<AddressDTO>> createByLogged(@RequestBody @Valid Address address){
        return ResponseEntity.ok(service.createByLogged(address));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AddressDTO> update(@PathVariable("id") Long id,@RequestBody @Valid AddressDTO addressDTO){
        return ResponseEntity.ok(service.update(id,addressDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/ByAccount/{id}")
    public ResponseEntity<List<AddressDTO>> getByAccount(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getByAccount(id));
    }

    @GetMapping("/byLogged")
    public ResponseEntity<List<AddressDTO>> getByLogged(){
        return ResponseEntity.ok(service.getByLogged());
    }

    @GetMapping("/ByEstablishment/{id}")
    public ResponseEntity<AddressDTO> getByEstablishment(@PathVariable("id") String id){
        //return ResponseEntity.ok(service.getByEstablishment(id));

        return ResponseEntity.ok(null);
    }

    @GetMapping("/isDelivery")
    public ResponseEntity<?> isDelivery(
            @RequestParam(value = "zipCode", required = false) String cep,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "lat", required = false) String lat,
            @RequestParam(value = "log", required = false) String log){

        Address address = Address.builder().zipCode(cep).street(street).lat(lat).log(log).build();

        return ResponseEntity.ok(service.isDelivery(address));

        //return ResponseEntity.ok(null);
    }
}
