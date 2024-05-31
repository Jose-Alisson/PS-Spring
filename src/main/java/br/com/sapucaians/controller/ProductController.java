package br.com.sapucaians.controller;

import br.com.sapucaians.dto.ProductDTO;
import br.com.sapucaians.model.Product;
import br.com.sapucaians.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid Product product){
        return ResponseEntity.ok(service.create(product));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid ProductDTO product){
        return ResponseEntity.ok(service.update(id,product));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/search")
    public ResponseEntity<?> search (@RequestParam("s") String s){
        return ResponseEntity.ok(service.search(s));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/offset/{offset}")
    public ResponseEntity<?> getByOffSet(@PathVariable("offset") int number){
        return ResponseEntity.ok(service.getByOffSet( number));
    }

    @GetMapping("/size")
    public ResponseEntity<?> getSIze(){
        return ResponseEntity.ok(service.getSize());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(BindException ex) {
        Map<String, String> errorsMap = new HashMap<>();
        List<FieldError> errors = ex.getFieldErrors();

        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errorsMap.put(field, message);
        }

        return errorsMap;
    }
}
