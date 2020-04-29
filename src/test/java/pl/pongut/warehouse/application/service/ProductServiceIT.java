package pl.pongut.warehouse.application.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static pl.pongut.warehouse.application.service.testFixtures.ProductTestFixtures.testProduct;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ProductServiceIT {

    public static final String PRODUCT_ID_1 = "111111111111111111111111";
    public static final String PRODUCT_ID_2 = "222222222222222222222222";
    public static final String PRODUCT_ID_3 = "333333333333333333333333";
    public static final String PRODUCT_ID_4 = "444444444444444444444444";

    @Autowired
    private ProductRepository productRepository;

    private static Stream<Arguments> paramsProvider() {
        return Stream.of(
            Arguments.of(
                GetAllProductsDto.builder().build(),
                List.of(PRODUCT_ID_1, PRODUCT_ID_2, PRODUCT_ID_3, PRODUCT_ID_4)
            ),
            Arguments.of(
                GetAllProductsDto.builder().productName("lek").build(),
                List.of(PRODUCT_ID_1, PRODUCT_ID_3, PRODUCT_ID_4)
            ),
            Arguments.of(
                GetAllProductsDto.builder().priceTo(4d).build(),
                List.of(PRODUCT_ID_1)
            ),
            Arguments.of(
                GetAllProductsDto.builder().productName("lek").priceFrom(10d).build(),
                List.of(PRODUCT_ID_4)
            ),
            Arguments.of(
                GetAllProductsDto.builder().categoryName("electronics").build(),
                List.of(PRODUCT_ID_1, PRODUCT_ID_3)
            ),
            Arguments.of(
                GetAllProductsDto.builder().categoryName("electron").build(),
                List.of()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("paramsProvider")
    public void findAllWithFilters(GetAllProductsDto searchParamsDto, List<String> expectedOutput) {
        //given
        final ProductService productService = new ProductService(productRepository, new ProductMapper());

        productRepository.saveAll(List.of(
            testProduct(new ObjectId(PRODUCT_ID_1)).toBuilder().productName("mleko").unitPrice(3d).categoryName("electronics").build(),
            testProduct(new ObjectId(PRODUCT_ID_2)).toBuilder().productName("dupa").unitPrice(5d).categoryName("sport").build(),
            testProduct(new ObjectId(PRODUCT_ID_3)).toBuilder().productName("aleks").unitPrice(8d).categoryName("electronics").build(),
            testProduct(new ObjectId(PRODUCT_ID_4)).toBuilder().productName("aleks").unitPrice(15d).categoryName("shoes").build()
        )).blockLast();

        //when
        final Flux<ProductDto> productsFlux = productService.findAll(searchParamsDto);

        //then
        StepVerifier.create(productsFlux)
            .recordWith(ArrayList::new)
            .expectNextCount(expectedOutput.size())
            .consumeRecordedWith(foundProducts -> {
                    assertThat(
                        foundProducts.stream().map(i -> i.get_id().toString()).collect(Collectors.toList()),
                        containsInAnyOrder(expectedOutput.toArray())
                    );
                }
            ).verifyComplete();
    }
}
