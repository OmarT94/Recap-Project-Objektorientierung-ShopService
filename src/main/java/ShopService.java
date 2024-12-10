import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();


    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order addOrder(List<String> productIds) {
        Set<String> uniqueProductIds = new HashSet<>(productIds);
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
        Order newOrder = new Order(UUID.randomUUID().toString(),
                products,
                OrderStatus.PROCESSING,
                Instant.now());

        return orderRepo.addOrder(newOrder);

    }

    public List<Order> getOrdersByStatus(OrderStatus status ) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.status().equals(status))
                .toList();
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        // Find the order by ID
        Order existingOrder = orderRepo.getOrderById(orderId);

        if (existingOrder == null) {
            throw new ProductNotFoundException("Order with ID " + orderId + " not found");
        }

        // Use @With to create a new instance with the updated status
        Order updatedOrder = existingOrder.withStatus(newStatus);

        // Remove the old order and replace it with the updated one
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(updatedOrder);

        return updatedOrder;
    }

    public Map<OrderStatus,Order> getOldestOrderPerStatus(){
        List<Order>orders =orderRepo.getOrders();

        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order ::status,
                        Collectors.minBy(Comparator.comparing(Order::timestamp))
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().orElse(null)
                ));
    }




}
