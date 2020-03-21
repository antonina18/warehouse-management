package pl.pongut.warehouse.application.handlers;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.pongut.warehouse.data.product.Product;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductHandler {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductHandler(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Mono<ServerResponse> handleGet(ServerRequest request) {
        Mono<ProductDto> productDto = productRepository.findById(request.pathVariable("id"))
                .map(productMapper::mapToProductDto);
        return ok().body(productDto, Product.class).switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handleGetAll(ServerRequest request) {
        Flux<ProductDto> products = productRepository.findAll()
                .map(productMapper::mapToProductDto)
                .collect(Collectors.toList())
                .flatMapMany(Flux::fromIterable);
        return ok().contentType(APPLICATION_JSON).body(products, Product.class);
    }
}
