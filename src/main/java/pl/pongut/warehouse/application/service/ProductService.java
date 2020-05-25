package pl.pongut.warehouse.application.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.data.product.QProduct;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.Page;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Flux<ProductDto> findAll(GetAllProductsDto getAllProductsDto) {
        return productRepository
            .findAll(buildSearchPredicate(getAllProductsDto))
            .map(productMapper::toProductDto)
            .collect(toList())
            .flatMapMany(Flux::fromIterable);
    }

    public Mono<Page<ProductDto>> findAllPaged(GetAllProductsDto getAllProductsDto, Pageable pageable) {
        return productRepository
            .findAll(buildSearchPredicate(getAllProductsDto), pageable)
            .map(p -> new Page<>(
                p.getPageSize(),
                p.getPageNumber(),
                p.getTotalItems(),
                p.getItems().stream().map(productMapper::toProductDto).collect(toList())
                ));
    }

    private BooleanBuilder buildSearchPredicate(GetAllProductsDto dto) {
        BooleanExpression productNamePredicate = Optional.ofNullable(dto.getProductName())
            .map(QProduct.product.productName::contains).orElse(null);

        BooleanExpression categoryNamePredicate = Optional.ofNullable(dto.getCategoryName())
            .map(QProduct.product.categoryName::eq).orElse(null);

        BooleanExpression priceGreaterOrEqual = Optional.ofNullable(dto.getPriceFrom())
            .map(QProduct.product.unitPrice::goe).orElse(null);

        BooleanExpression priceLowerOrEqual = Optional.ofNullable(dto.getPriceTo())
            .map(QProduct.product.unitPrice::loe).orElse(null);

        return new BooleanBuilder()
            .and(productNamePredicate)
            .and(categoryNamePredicate)
            .and(priceGreaterOrEqual)
            .and(priceLowerOrEqual);
    }
}
