package br.edu.shopsphere.patterns.observer;
public class CustomerObserver implements OrderObserver {
 public void update(String id,String event){System.out.println("CUSTOMER "+id+" "+event);}
}
