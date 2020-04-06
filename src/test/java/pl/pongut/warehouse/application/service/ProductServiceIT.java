package pl.pongut.warehouse.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pl.pongut.warehouse.application.service.testFixtures.ProductTestFixtures.testProduct;

//@DataMongoTest
@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
public class ProductServiceIT {

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private ProductService productService;

    @Test
    @DisplayName("Should find all when dto empty")
    public void findAll() {
        //given
        String productId_1 = "111111111111111111111111";
        String productId_2 = "222222222222222222222222";
        productRepository.saveAll(List.of(
                testProduct(productId_1),
                testProduct(productId_2)
        ));
        GetAllProductsDto dto = GetAllProductsDto.builder().build();

        //when
//        Flux<ProductDto> productsFlux = productService.findAll(dto);
        Flux<String> productsFlux = Flux.just("123","123");

        //then
//        StepVerifier.create(productsFlux)
//                .recordWith(List::of)
//                .expectNextCount(2)
//                .consumeRecordedWith(foundProducts ->
//                        assertThat(
//                                foundProducts.stream().map(i -> i.get_id().toString()).collect(Collectors.toList()),
//                                is(List.of(productId_1, productId_2))
//                        ))
//                .expectComplete()
//                .verify();
    }

    @DisplayName("Should filter by product name")
    @Test
    public void findByProductName() {
        //given
        final String productName1 = "mleko";
        final String productName2 = "mleczna kanapka";
        final String productName3 = "wieprzowina";

        productRepository.saveAll(List.of(
                testProduct().toBuilder().productName(productName1).build(),
                testProduct().toBuilder().productName(productName2).build(),
                testProduct().toBuilder().productName(productName3).build()
        ));
        final GetAllProductsDto dto = GetAllProductsDto.builder()
                .productName(Optional.of("mle"))
                .build();

        //when
//        final Flux<ProductDto> productsFlux = productService.findAll(dto);
        Flux<String> productsFlux = Flux.just("123","123");


        //then
//        StepVerifier.create(productsFlux)
//                .recordWith(List::of)
//                .consumeRecordedWith(foudnProducts ->
//                        assertThat(
//                                foudnProducts.stream().map(ProductDto::getProductName).collect(Collectors.toList()),
//                                is(List.of("mleko, mleczna kanapka")
//                                )
//                        ))
//                .expectComplete()
//                .verify();
    }
}
