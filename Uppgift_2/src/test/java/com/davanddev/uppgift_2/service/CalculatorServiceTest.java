package com.davanddev.uppgift_2.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testAdd() {
        int result = calculatorService.add(10, 5);
        assertEquals(15, result);
    }

    @Test
    void testSubtract() {
        int result = calculatorService.subtract(10, 5);
        assertEquals(5, result);
    }

    @Test
    void testMultiply() {
        int result = calculatorService.multiply(10, 5);
        assertEquals(50, result);
    }

    @Test
    void testDivide() {
        int result = calculatorService.divide(10, 5);
        assertEquals(2, result);
    }

    @Test
    void testDivideByZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculatorService.divide(10, 0)
        );
        assertEquals("Division by zero is not allowed.", exception.getMessage());
    }
}
