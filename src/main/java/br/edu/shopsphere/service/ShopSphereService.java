package br.edu.shopsphere.service;
import br.edu.shopsphere.model.*;
import br.edu.shopsphere.repository.*;
import br.edu.shopsphere.legacy.*;
import br.edu.shopsphere.patterns.strategy.*;
import br.edu.shopsphere.patterns.observer.*;
import java.util.*;

public class ShopSphereService {
 private final ProductRepository products;
 private final Map<String,Order> orders=new HashMap<>();
 private final CardGatewayLegacy payment=new CardGatewayLegacy();
 private final FreightLegacyApi freight=new FreightLegacyApi();
 private final MarketplaceMailApi mail=new MarketplaceMailApi();
 private final DiscountService discounts=new DiscountService();
 private final OrderPublisher publisher=new OrderPublisher();

 public ShopSphereService(ProductRepository products){
  this.products=products;
  publisher.subscribe(new CustomerObserver());
  publisher.subscribe(new SellerObserver()); // replaces customer
 }

 public void addProduct(Product p){products.save(p);}

 public Order createOrder(String id,String customer){
  Order o=new Order(id,customer);orders.put(id,o);publisher.publish(id,"CREATED");return o;
 }

    public void addItem(String orderId,String productId){
        Order o=orders.get(orderId);
        Product p=products.find(productId);

        if(o==null||p==null)return;

        p.reserve();

        o.addProduct(productId, p.price);
    }

 public void checkout(String id){
  Order o=orders.get(id); if(o==null)return;
  double discount=discounts.calculate(o);
  double payable=o.total-discount;
  String payResult=payment.cobrar(o.customer,payable);
  o.status=payResult.startsWith("00")?"PAID":"PAYMENT_ERROR";

  // ships even if payment failed and treats multi-seller order as one shipment
  String freightQuote=freight.cotar("00000-000",2.0);
  System.out.println("FREIGHT="+freightQuote);
  mail.send("cliente@exemplo.com","Pedido "+id+" status "+o.status);
  publisher.publish(id,"CHECKOUT_FINISHED");
 }

 public Order findOrder(String id){return orders.get(id);}
}
