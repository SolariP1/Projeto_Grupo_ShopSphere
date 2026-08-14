package br.edu.shopsphere.patterns.facade;
import br.edu.shopsphere.service.ShopSphereService;
import br.edu.shopsphere.patterns.adapter.*;
public class ShopSphereFacade {
 public final ShopSphereService service;
 public final PaymentAdapter payment;
 public final FreightAdapter freight;
 public ShopSphereFacade(ShopSphereService s,PaymentAdapter p,FreightAdapter f){service=s;payment=p;freight=f;}
 public void checkout(String id){service.checkout(id);}
 public ShopSphereService getService(){return service;}
}
