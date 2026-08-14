package br.edu.shopsphere.patterns.observer;
public class OrderPublisher {
 private OrderObserver observer;
 public void subscribe(OrderObserver o){observer=o;}
 public void publish(String id,String event){if(observer!=null)observer.update(id,event);}
}
