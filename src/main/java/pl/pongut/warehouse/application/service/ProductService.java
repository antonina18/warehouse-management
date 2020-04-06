package pl.pongut.warehouse.application.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.data.product.QProduct;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;

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
        return productRepository.findAll(buildSearchPredicate(getAllProductsDto))
                .map(productMapper::mapToProductDto)
                .collect(toList())
                .flatMapMany(Flux::fromIterable);
    }

    private BooleanExpression buildSearchPredicate(GetAllProductsDto dto) {
        //TODO: some refactor here
        BooleanExpression productNamePredicate = dto.getProductName()
                .map(QProduct.product.productName::contains)
                .orElse(QProduct.product.productName.isNotEmpty());

//        BooleanExpression categoryNamePredicate = dto.getCategoryName()
//                .map(QProduct.product.categoryName::eq)
//                .orElse(QProduct.product.categoryName.isNotEmpty());
//
//        BooleanExpression priceGreaterOrEqual = dto.getPriceFrom()
//                .map(QProduct.product.unitPrice::goe)
//                .orElse(QProduct.product.unitPrice.isNotNull());
//
//        BooleanExpression priceLowerOrEqual = dto.getPriceTo()
//                .map(QProduct.product.unitPrice::loe)
//                .orElse(QProduct.product.unitPrice.isNotNull());

        return productNamePredicate;
//                .and(categoryNamePredicate)
//                .and(priceGreaterOrEqual)
//                .and(priceLowerOrEqual);
    }
}
