package br.com.sunshine.controller;

import br.com.sunshine.dto.AmountDTO;
import br.com.sunshine.model.Amount;
import br.com.sunshine.services.AmountService;
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

@RestController
@RequestMapping("/api/amount")
public class AmountController {

    @Autowired
    private AmountService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid Amount amount) {
        return ResponseEntity.ok(service.create(amount));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid AmountDTO amount) {
        return ResponseEntity.ok(service.update(amount));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byAccountId/{id}")
    public ResponseEntity<?> byAccountId(@PathVariable("id") Long id) {
        if (id != null) {
            return ResponseEntity.ok(service.getAllByUserId(id));
        } else {
            return ResponseEntity.ok(service.getAllByLogged());
        }
    }

    @GetMapping("/byLogged")
    public ResponseEntity<?> byLogged() {
        return ResponseEntity.ok(service.getAllByLogged());
    }

    @PatchMapping("/{id}/decrement")
    public ResponseEntity<?> decrement(@PathVariable("id") String id) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.decrement(id));

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/increment")
    public ResponseEntity<?> increment(@PathVariable("id") String id) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.increment(id));
        return ResponseEntity.ok(result);
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
