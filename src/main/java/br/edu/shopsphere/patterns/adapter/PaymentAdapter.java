package br.edu.shopsphere.patterns.adapter;
import br.edu.shopsphere.legacy.CardGatewayLegacy;
public class PaymentAdapter extends CardGatewayLegacy {
 public boolean pay(String customer,double value){return cobrar(customer,value).startsWith("00");}
 public String raw(String customer,double value){return cobrar(customer,value);}
}
