package ws_consumer;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import crud_soap.WebService.model.Product;
import crud_soap.WebService.service.ProductService;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class ConsumerWS {

    public static void main(String[] args) throws Exception {
        
   	/*  Yeh line aapke SOAP service ka WSDL URL specify karti hai.
     *  WSDL file service ka structure aur operations define karti hai.  */
        URL url = new URL("http://localhost:8081/webservice/product?wsdl");


    /* The QName is used to uniquely identify the service within a particular namespace.
     * The namespace is typically the URL, and the local part is the name of the service in the WSDL file.
     * "http://service.WebService.crud_soap/" namespace URI hai, jo service ko uniquely identify karta hai, that avoids naming conflicts between different services.
     * "ProductServiceImplService"  WSDL file mein service ka local name hai.    */
        QName qname = new QName("http://service.WebService.crud_soap/", "ProductServiceImplService");

    /* Yeh line ek Service object create karti hai jo SOAP web service ko represent karta hai.
     * Yeh object WSDL URL aur QName ke saath initialize hota hai.  */
        Service service = Service.create(url, qname);

    /* Yeh line proxy object (client stub) ko return karti hai jo ProductService interface ko implement karta hai.
     * "getPort(ProductService.class)" method proxy object ko create karta hai,
     * jo SOAP service ke operations ko local method ki tarah call karne ke liye use hota hai.
     */
       ProductService productService = service.getPort(ProductService.class);

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View All Products");
            System.out.println("2. View Product by ID");
            System.out.println("3. Add a New Product");
            System.out.println("4. Update a Product");
            System.out.println("5. Delete a Product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // View all products
                    List<Product> products = productService.getAllProduct();
                    System.out.println("List of Products: ");
                    products.forEach(System.out::println);
                    break;

                    
                case 2:
                    // View a product by ID
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    Product product = productService.getProduct(productId);
                    if (product != null) {
                        System.out.println("Product with ID " + productId + ": " + product);
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                    
                case 3:
                    // Add a new product
                    System.out.print("Enter Product ID: ");
                    int newId = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    System.out.print("Enter Product Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter Product Price: ");
                    double newPrice = scanner.nextDouble();

                    Product newProduct = new Product(newId, newName, newPrice);
                    String addResponse = productService.createProduct(newProduct);
                    System.out.println(addResponse);
                    break;

                    
                case 4:
                    // Update an existing product
                    System.out.print("Enter Product ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    System.out.print("Enter New Product Name: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter New Product Price: ");
                    double updatePrice = scanner.nextDouble();

                    Product updatedProduct = new Product(updateId, updateName, updatePrice);
                    String updateResponse = productService.updateProduct(updatedProduct);
                    System.out.println(updateResponse);
                    break;

                    
                case 5:
                    // Delete a product
                    System.out.print("Enter Product ID to delete: ");
                    int deleteId = scanner.nextInt();
                    String deleteResponse = productService.deleteProduct(deleteId);
                    System.out.println(deleteResponse);
                    break;

                    
                case 6:
                    // Exit the program
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}