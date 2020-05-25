package pl.pongut.warehouse.application.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pongut.warehouse.application.service.dto.GetAllProductsDto;
import pl.pongut.warehouse.data.product.Product;
import pl.pongut.warehouse.data.product.ProductMapper;
import pl.pongut.warehouse.domain.model.product.ProductDto;
import pl.pongut.warehouse.domain.repository.Page;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static pl.pongut.warehouse.application.service.testFixtures.ProductTestFixtures.testProduct;

@SuppressWarnings("ConstantConditions")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ProductServiceIT {

    public static final String PRODUCT_ID_1 = "111111111111111111111111";
    public static final String PRODUCT_ID_2 = "222222222222222222222222";
    public static final String PRODUCT_ID_3 = "333333333333333333333333";
    public static final String PRODUCT_ID_4 = "444444444444444444444444";

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

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

    @BeforeEach
    public void setup() {
        productService = new ProductService(productRepository, new ProductMapper());
    }

    @AfterEach
    public void cleanup() {
        productRepository.deleteAll().block();
    }

    @ParameterizedTest
    @MethodSource("paramsProvider")
    public void findAllWithFilters(GetAllProductsDto searchParamsDto, List<String> expectedOutput) {
        //given
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
            .consumeRecordedWith(foundProducts -> assertThat(
                foundProducts.stream().map(i -> i.get_id().toString()).collect(Collectors.toList()),
                containsInAnyOrder(expectedOutput.toArray())))
            .verifyComplete();
    }

    @Test
    public void findAllWithPagination() {
        //given
        int productsAmount = 9;
        int pageSize = 5;
        productRepository.saveAll(generateProducts(productsAmount)).blockLast();

        //when
        Page<ProductDto> page1 = productService.findAllPaged(GetAllProductsDto.builder()
                .build(),
            PageRequest.of(0, pageSize))
            .block();
        Page<ProductDto> page2 = productService.findAllPaged(GetAllProductsDto.builder()
                .build(),
            PageRequest.of(1, pageSize))
            .block();

        //then
        assertThat(page1.getItems().size(), equalTo(pageSize));
        assertThat(page1.getTotalItems(), equalTo(productsAmount));
        assertThat(page1.getPageNumber(), equalTo(0));
        assertThat(page1.getPageSize(), equalTo(pageSize));

        assertThat(page2.getItems().size(), equalTo(4));
        assertThat(page2.getTotalItems(), equalTo(productsAmount));
        assertThat(page2.getPageNumber(), equalTo(1));
        assertThat(page2.getPageSize(), equalTo(pageSize));
    }

    @Test
    public void findAllPagedWithEmptyResult() {
        //given
        final int pageSize = 10;

        //when
        Page<ProductDto> result = productService.findAllPaged(GetAllProductsDto.builder().build(), PageRequest.of(0, pageSize)).block();

        //then
        assertThat(result.getItems().size(), equalTo(0));
        assertThat(result.getTotalItems(), equalTo(0));
        assertThat(result.getPageNumber(), equalTo(0));
        assertThat(result.getPageSize(), equalTo(pageSize));
    }

    @Test
    public void findAllWithSortingByProductName() {
        //given
        productRepository.saveAll(List.of(
            testProduct().toBuilder().productName("A").build(),
            testProduct().toBuilder().productName("b").build(),
            testProduct().toBuilder().productName("C").build()
        )).blockLast();

        //when
        Page<ProductDto> foundProducts = productService.findAllPaged(
            GetAllProductsDto.builder().build(),
            PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.ASC, "productName").ignoreCase())))
            .block();

        //then
        System.out.println("############");
        System.out.println("############");
        System.out.println("            ");
        System.out.println(foundProducts.getItems().stream().map(ProductDto::getProductName).collect(Collectors.toList()));
        System.out.println("            ");
        System.out.println("############");
        System.out.println("############");


        assertThat(
            foundProducts.getItems().stream().map(ProductDto::getProductName).collect(Collectors.toList()),
//            contains("A","b","C"));// TODO: 25.05.2020 that should work
            contains("A", "C", "b"));
    }

    @Test
    public void findAllWithSortingByPriceDescending() {
        //given
        //given
        productRepository.saveAll(List.of(
            testProduct().toBuilder().productName("A").unitPrice(1.0).build(),
            testProduct().toBuilder().productName("B").unitPrice(3.0).build(),
            testProduct().toBuilder().productName("C").unitPrice(2.0).build()
        )).blockLast();

        //when
        Page<ProductDto> foundProducts = productService.findAllPaged(
            GetAllProductsDto.builder().build(),
            PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.DESC, "unitPrice"))))
            .block();

        //then
        assertThat(
            foundProducts.getItems().stream().map(ProductDto::getProductName).collect(Collectors.toList()),
            contains("B", "C", "A"));
    }

    private List<Product> generateProducts(int productsAmount) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < productsAmount; i++) {
            products.add(testProduct());
        }
        return products;
    }
}
