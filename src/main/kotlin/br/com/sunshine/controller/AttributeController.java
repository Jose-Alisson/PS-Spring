package br.com.sunshine.controller;

import br.com.sunshine.dto.AttributeDTO;
import br.com.sunshine.model.Attribute;
import br.com.sunshine.services.AttributeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/product/attribute")
@RestController
public class AttributeController {

    @Autowired
    private AttributeService service;

    @GetMapping("/create")
    public ResponseEntity<AttributeDTO> create(@RequestBody @Valid Attribute attr){
        return ResponseEntity.ok(service.create(attr));
    }

    @DeleteMapping("/update")
    public ResponseEntity<AttributeDTO> update(@RequestBody @Valid AttributeDTO attr){
        return ResponseEntity.ok(service.update(attr));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/allByProduct/{id}")
    public ResponseEntity<List<AttributeDTO>> getAllByProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getAllByProduct(id));
    }
}
