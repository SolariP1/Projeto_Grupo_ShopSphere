package br.edu.shopsphere.model;
public class Product {
 public String id; public String sellerId; public String name; public double price; public int stock;
 public Product(String id,String sellerId,String name,double price,int stock){
  this.id=id;this.sellerId=sellerId;this.name=name;this.price=price;this.stock=stock;
 }
}
