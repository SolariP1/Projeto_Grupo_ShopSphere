package br.edu.shopsphere.patterns.factory;
import br.edu.shopsphere.model.Order;
public class OrderFactory {
 public static Order create(String type,String id,String customer){
  Order o=new Order(id,customer);
  if("MARKETPLACE".equals(type)) o.status="CREATED_MARKETPLACE";
  else if("DIRECT".equals(type)) o.status="CREATED_DIRECT";
  return o;
 }
}
