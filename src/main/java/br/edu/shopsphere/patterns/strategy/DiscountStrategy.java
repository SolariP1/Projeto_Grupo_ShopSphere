package br.edu.shopsphere.patterns.strategy;
import br.edu.shopsphere.model.Order;
public interface DiscountStrategy { double calculate(Order order); }
