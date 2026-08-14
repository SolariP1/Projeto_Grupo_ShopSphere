package br.edu.shopsphere.model;
import java.util.*;
public class Order {
 public String id; public String customer; public String status="CREATED"; public double total;
 public List<String> productIds=new ArrayList<>();
 public Order(String id,String customer){this.id=id;this.customer=customer;}
}
