package br.edu.shopsphere;
import br.edu.shopsphere.model.*;
import br.edu.shopsphere.repository.*;
import br.edu.shopsphere.service.*;
import br.edu.shopsphere.patterns.facade.*;
import br.edu.shopsphere.patterns.adapter.*;

public class Main {
 public static void main(String[] args){
  ProductRepository repo=new ProductRepository();
  ShopSphereService service=new ShopSphereService(repo);
  service.addProduct(new Product("P1","V1","Notebook",3500.00,2));
  service.addProduct(new Product("P2","V2","Mouse",120.00,5));
  service.createOrder("O1","Cliente Demo");
  service.addItem("O1","P1");
  service.addItem("O1","P2");
  ShopSphereFacade facade=new ShopSphereFacade(service,new PaymentAdapter(),new FreightAdapter());
  facade.checkout("O1");
  System.out.println("FINAL="+facade.getService().findOrder("O1").status);
 }
}
