package pl.pongut.warehouse.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.pongut.warehouse.data.product.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
