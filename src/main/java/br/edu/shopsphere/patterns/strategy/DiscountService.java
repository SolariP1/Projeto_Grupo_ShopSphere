package br.edu.shopsphere.patterns.strategy;
import br.edu.shopsphere.model.Order;
public class DiscountService {
 private DiscountStrategy strategy;
 public void setStrategy(DiscountStrategy s){strategy=s;}
 public double calculate(Order o){
  if(o.total>1000) return o.total*0.20;
  if(o.productIds.size()>=3) return o.total*0.10;
  return strategy==null?0:strategy.calculate(o);
 }
}
