import java.util.List;

public class Main {
    public static void main(String[] args) {
       //Repositories
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();

        //Instantiate ShopService with the repositories
        ShopService shopService = new ShopService(productRepo, orderRepo);

        //Define products
//        List<Product> products =List.of(
//                new Product("1","Orange"),
//                new Product("2","Apple")
//        );
//
//        // Add products to the productRepo
//        for (Product product : products) {
//           productRepo.addProduct(product);
//        }

        List<String> order1Product = List.of("2","2");
        List<String> order2Products = List.of("1", "1");

        Order order1 = shopService.addOrder(order1Product);
        Order order2 = shopService.addOrder(order2Products);

        System.out.println(order1);
        System.out.println(order2);







    }
}
