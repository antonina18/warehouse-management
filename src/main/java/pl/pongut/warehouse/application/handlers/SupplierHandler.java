package pl.pongut.warehouse.application.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.pongut.warehouse.data.supplier.Supplier;
import pl.pongut.warehouse.data.supplier.SupplierMapper;
import pl.pongut.warehouse.domain.model.supplier.SupplierDto;
import pl.pongut.warehouse.domain.repository.SupplierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class SupplierHandler {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierHandler(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public Mono<ServerResponse> handleGet(ServerRequest request) {
        Mono<SupplierDto> supplierDto = supplierRepository.findById(request.pathVariable("id"))
                .map(supplierMapper::mapToSupplierDto);
        return ok().body(supplierDto, Supplier.class).switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handleGetAll(ServerRequest request) {
        Flux<SupplierDto> suppliers = supplierRepository.findAll()
                .map(supplierMapper::mapToSupplierDto)
                .collect(Collectors.toList())
                .flatMapMany(Flux::fromIterable);
        return ok().contentType(APPLICATION_JSON).body(suppliers, Supplier.class);
    }
}
