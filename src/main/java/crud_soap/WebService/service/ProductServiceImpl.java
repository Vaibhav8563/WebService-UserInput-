package crud_soap.WebService.service;



import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

import crud_soap.WebService.model.Product;


@WebService(endpointInterface = "crud_soap.WebService.service.ProductService")
public class ProductServiceImpl implements ProductService {

    private static List<Product> products = new ArrayList<>();


    @Override
    public String createProduct(Product product) {
        if (getProduct(product.getId()) != null) {
            return "Product with this ID already exists.";
        }
        products.add(product);
        return "Product added successfully";
    }

    @Override
    public Product getProduct(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Product> getAllProduct() {
        return products;
    }

    @Override
    public String updateProduct(Product product) {
        Product existingProduct = getProduct(product.getId());
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return "Product updated successfully";
        }
        return "Product not found";
    }

    @Override
    public String deleteProduct(int id) {
        Product product = getProduct(id);
        if (product != null) {
            products.remove(product);
            return "Product with ID " + id + " successfully deleted.";
        } else {
            return "Product with ID " + id + " not found.";
        }
    }
}
