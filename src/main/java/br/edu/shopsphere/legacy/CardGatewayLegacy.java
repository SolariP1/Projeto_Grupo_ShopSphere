package br.edu.shopsphere.legacy;
public class CardGatewayLegacy {
 public String cobrar(String cliente,double valor){return valor>0?"00|APPROVED":"99|ERROR";}
}
