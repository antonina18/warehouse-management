package pl.pongut.warehouse.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import pl.pongut.warehouse.data.product.Product;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ReactiveQuerydslPredicateExecutor<Product> {
    public Flux<Product> findAllByProductNameLikeOrUnitPriceIsLessThan(String name, BigDecimal price);
}
