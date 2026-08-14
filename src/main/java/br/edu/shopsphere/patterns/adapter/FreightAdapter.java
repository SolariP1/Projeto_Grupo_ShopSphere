package br.edu.shopsphere.patterns.adapter;
import br.edu.shopsphere.legacy.FreightLegacyApi;
public class FreightAdapter {
 private final FreightLegacyApi api=new FreightLegacyApi();
 public double quote(String zip,double weight){
  String[] p=api.cotar(zip,weight).split(":");
  return Double.parseDouble(p[1]);
 }
 public FreightLegacyApi legacy(){return api;}
}
