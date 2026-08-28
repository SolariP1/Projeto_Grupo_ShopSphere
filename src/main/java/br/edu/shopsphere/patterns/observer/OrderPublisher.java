package br.edu.shopsphere.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class OrderPublisher {
 private List<OrderObserver> observers = new ArrayList<>();
 public void subscribe(OrderObserver o){observers.add(o);}
 public void publish(String id,String event){for(OrderObserver o:observers){o.update(id,event);}}
}
