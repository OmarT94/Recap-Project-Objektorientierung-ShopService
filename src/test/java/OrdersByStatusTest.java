import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdersByStatusTest {

    @Test
     void testGetOrdersByStatus() {

        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Apfel"));
        OrderRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo,orderRepo);
        List<Product> products = List.of(new Product("1", "Apfel"));
        Order order1 = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());
        Order order2 = new Order(UUID.randomUUID().toString(), products, OrderStatus.COMPLETED,Instant.now());
        Order order3 = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);

        // Get processing orders
        List<Order> processingOrders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        Assertions.assertEquals(2, processingOrders.size());
        Assertions.assertTrue(processingOrders.stream().allMatch(order -> order.status() == OrderStatus.PROCESSING));
    }


}
