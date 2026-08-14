package br.edu.shopsphere.patterns.abstractfactory;
import br.edu.shopsphere.legacy.*;
public class CommerceFamilyFactory {
 public Object payment(String family){return new CardGatewayLegacy();}
 public Object freight(String family){return new FreightLegacyApi();}
 public Object notification(String family){return new MarketplaceMailApi();}
}
