import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();


    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
//        for (String productId : productIds) {
//            Optional <Product> productToOrder = productRepo.getProductById(productId);
//            if (productToOrder.isEmpty()) {
//                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
//                return null;
//            }
//            products.add(productToOrder.get());
//        }
//
//        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);
//
//        return orderRepo.addOrder(newOrder);
        for (String productId : productIds) {
            // Retrieve product using Optional and throw an exception if not found
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

            // Add the found product to the list
            products.add(productToOrder);
        }
        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);

    }

    public List<Order> getOrdersByStatus(OrderStatus status ) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(status))
                .toList();
    }
}
