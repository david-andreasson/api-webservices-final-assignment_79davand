package com.davanddev.uppgift_2.controller;

import com.davanddev.uppgift_2.model.CalculatorRequest;
import com.davanddev.uppgift_2.model.CalculatorResponse;
import com.davanddev.uppgift_2.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/add")
    public ResponseEntity<CalculatorResponse> add(@RequestBody CalculatorRequest request) {
        int result = calculatorService.add(request.getA(), request.getB());
        CalculatorResponse response = new CalculatorResponse(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subtract")
    public ResponseEntity<CalculatorResponse> subtract(@RequestBody CalculatorRequest request) {
        int result = calculatorService.subtract(request.getA(), request.getB());
        CalculatorResponse response = new CalculatorResponse(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/multiply")
    public ResponseEntity<CalculatorResponse> multiply(@RequestBody CalculatorRequest request) {
        int result = calculatorService.multiply(request.getA(), request.getB());
        CalculatorResponse response = new CalculatorResponse(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/divide")
    public ResponseEntity<CalculatorResponse> divide(@RequestBody CalculatorRequest request) {
        int result = calculatorService.divide(request.getA(), request.getB());
        CalculatorResponse response = new CalculatorResponse(result);
        return ResponseEntity.ok(response);
    }
}
