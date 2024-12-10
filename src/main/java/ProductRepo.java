import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {
    private List<Product> products;
    ProductRepo productRepo;

    public ProductRepo() {
        products = new ArrayList<>();
        products.add(new Product("1", "Orange"));
        products.add(new Product("2", "Apple"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional <Product> getProductById(String id) {
        for (Product product : products) {
            if (product.id().equals(id)) {
                return Optional.of(product) ;
            }
        }
        return Optional.empty();
    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
//        for (Product product : products) {
//           if (product.id().equals(id)) {
//               products.remove(product);
//               return;
//           }
//        }
        products.removeIf(product -> product.id().equals(id));
    }
    public void findAndPrintProduct(String productId) {
        productRepo.getProductById(productId)
                .ifPresentOrElse(
                        product -> System.out.println("Found product: " + product.name()),
                        () -> System.out.println("Product with ID " + productId + " not found.")
                );
    }


}
