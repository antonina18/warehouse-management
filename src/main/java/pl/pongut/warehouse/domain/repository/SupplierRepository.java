package pl.pongut.warehouse.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.pongut.warehouse.data.supplier.Supplier;
import reactor.core.publisher.Flux;

public interface SupplierRepository extends ReactiveMongoRepository<Supplier, String> {

    Flux<Supplier> findByCompanyName(String name);
}
