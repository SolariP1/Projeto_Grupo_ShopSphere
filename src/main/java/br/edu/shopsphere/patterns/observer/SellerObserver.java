package br.edu.shopsphere.patterns.observer;
public class SellerObserver implements OrderObserver {
 public void update(String id,String event){System.out.println("SELLER "+id+" "+event);}
}
