package br.edu.shopsphere.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.shopsphere.model.Order;
import br.edu.shopsphere.model.Product;
import br.edu.shopsphere.repository.ProductRepository;

class ShopSphereServiceTest {

    private ShopSphereService service;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new ProductRepository();
        service = new ShopSphereService(repository);
        service.addProduct(new Product("p1", "seller1", "Produto 1", 100.0, 5));
    }

    @Test
    void createOrderShouldRegisterOrderAsCreated() {
        Order order = service.createOrder("o1", "cliente1");

        assertNotNull(order);
        assertEquals("CREATED", order.status);
        assertEquals(service.findOrder("o1"), order);
    }

    @Test
    void addItemShouldAddProductAndUpdateTotal() {
        service.createOrder("o1", "cliente1");

        service.addItem("o1", "p1");

        Order order = service.findOrder("o1");
        assertEquals(1, order.productIds.size());
        assertEquals(100.0, order.total);
    }

    @Test
    void checkoutShouldMarkOrderAsPaidWhenPaymentSucceeds() {
        service.createOrder("o1", "cliente1");
        service.addItem("o1", "p1");

        service.checkout("o1");

        Order order = service.findOrder("o1");
        assertEquals("PAID", order.status);
    }
}
