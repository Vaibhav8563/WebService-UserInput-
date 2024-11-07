package ws_publisher;

import javax.xml.ws.Endpoint;

import crud_soap.WebService.service.ProductServiceImpl;


public class PublisherWS {

    public static void main(String[] args) {
        // Publish the ProductService web service at the given URL
        Endpoint.publish("http://localhost:8081/webservice/product", new ProductServiceImpl());
        
        System.out.println("SOAP Publisher WebService is running........");
    }
}