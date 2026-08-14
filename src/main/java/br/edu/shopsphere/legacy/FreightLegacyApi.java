package br.edu.shopsphere.legacy;
public class FreightLegacyApi {
 public String cotar(String cep,double peso){return "EXPRESS:"+((peso*3.7)+18.0)+":2";}
}
