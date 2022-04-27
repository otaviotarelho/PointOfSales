package edu.otaciotarelho.pointofsale.business.external;

import edu.otaciotarelho.pointofsale.domain.external.Product;
import edu.otaciotarelho.pointofsale.domain.external.ProductService;
import org.springframework.stereotype.Service;

@Service("productIntegration/v1")
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProduct(String id) {
        return Product.builder().build();
    }

}
