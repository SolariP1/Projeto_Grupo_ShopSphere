package br.edu.shopsphere.repository;
import br.edu.shopsphere.model.Product;
import java.util.*;
public class ProductRepository {
 private final Map<String,Product> data=new HashMap<>();
 public void save(Product p){data.put(p.id,p);}
 public Product find(String id){return data.get(id);}
 public Collection<Product> all(){return data.values();}
}
