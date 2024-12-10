import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.COMPLETED);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void updateOrderTest() {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);
        List<Product> products = List.of(new Product("1","Apfel"));
        Order order = new Order(UUID.randomUUID().toString(), products, OrderStatus.IN_DELIVERY);
        orderRepo.addOrder(order);

        //When
        Order updatedOrder = shopService.updateOrder(order.id(), OrderStatus.IN_DELIVERY);

        //Then
        assertEquals(OrderStatus.IN_DELIVERY, updatedOrder.status());
        assertEquals(order.id(), updatedOrder.id());
        assertEquals(order.products(), updatedOrder.products());

    }
}
