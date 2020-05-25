package pl.pongut.warehouse.domain.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import pl.pongut.warehouse.data.product.Product;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ReactiveQuerydslPredicateExecutor<Product> {
    default Mono<Page<Product>> findAll(Predicate predicate, Pageable pageable) {
        return findAll(predicate, pageable.getSort())// TODO: case insensitivity does not work
            .collectList().map(list -> new Page<>(
                    pageable.getPageSize(),
                    pageable.getPageNumber(),
                    list.size(),
                    list.stream()
                        .skip(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .collect(Collectors.toList())
                )
            );
    }
}
