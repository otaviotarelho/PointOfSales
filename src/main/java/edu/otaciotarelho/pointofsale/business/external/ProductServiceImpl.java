package edu.otaciotarelho.pointofsale.business.external;

import edu.otaciotarelho.pointofsale.domain.exception.ProductNotFoundException;
import edu.otaciotarelho.pointofsale.domain.external.Product;
import edu.otaciotarelho.pointofsale.domain.external.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("productIntegration/v1")
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${product.url}")
    private String productUrl;

    @Override
    public Product getProduct(String id) {
        try{
            return restTemplate.getForObject(productUrl + "/products/" + id, Product.class);
        } catch (Exception e){
            throw new ProductNotFoundException();
        }
    }

}
