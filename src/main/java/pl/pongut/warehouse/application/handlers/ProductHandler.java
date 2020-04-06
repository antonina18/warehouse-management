package pl.pongut.warehouse.application.handlers;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.application.service.ProductService;
import pl.pongut.warehouse.data.product.Product;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductHandler {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductHandler(ProductRepository productRepository, ProductMapper productMapper, ProductService productService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productService = productService;
    }

    public Mono<ServerResponse> handleGet(ServerRequest request) {
        Mono<ProductDto> productDto = productRepository.findById(request.pathVariable("id"))
                .map(productMapper::mapToProductDto);
        return ok().body(productDto, Product.class).switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> handleGetAll(ServerRequest request) {
        Flux<ProductDto> products = productService.findAll(prepareProductSearchParams(request));
        return ok().contentType(APPLICATION_JSON).body(products, Product.class);
    }

    //TODO: some mapper here needed
    private GetAllProductsDto prepareProductSearchParams(ServerRequest request) {
        return GetAllProductsDto.builder()
                .productName(request.queryParam("productName"))
                .productName(request.queryParam("categoryName"))
                .productName(request.queryParam("priceGreaterThan"))
                .productName(request.queryParam("priceLowerThan"))
                .build();
    }
}
