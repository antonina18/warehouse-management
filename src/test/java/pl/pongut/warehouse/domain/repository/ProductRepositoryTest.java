package pl.pongut.warehouse.domain.repository;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pongut.warehouse.data.product.Product;
import pl.pongut.warehouse.data.supplier.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private final Product first = Product.builder()
            ._id(new ObjectId("54759eb3c090d83494e2d804"))
            .categoryName("nothing special")
            .discount(false)
            .unitsInStock(1)
            .unitPrice(BigDecimal.TEN)
            .quantityPerUnit(1)
            .productName("milk")
            .supplier(new Supplier())
            .build();

    private final Product second = Product.builder()
            ._id(new ObjectId("54759ab3c090d83494e2d804"))
            .categoryName("nothing special")
            .discount(false)
            .unitsInStock(1)
            .unitPrice(BigDecimal.TEN)
            .quantityPerUnit(1)
            .productName("milk")
            .supplier(new Supplier())
            .build();

    @Test
    public void shouldReturnOneProductWithExpected2() {
        Flux<Product> setup = productRepository.deleteAll().thenMany(productRepository.saveAll(Flux.just(first, second)));

        Flux<Product> found = productRepository.findAll();
//        Flux<Product> composite = Flux.from(setup)
//                .thenMany(found);

        StepVerifier
                .create(found)
                .expectNext(first)
                .expectNext(second)
                .verifyComplete();

    }
    
    @Test
    public void shouldReturnOneProductWithExpected() {
        String expectedId = "54759ab3c090d83494e2d804";
        productRepository.save(first).block();
        Mono<Product> product = productRepository.findById(Mono.just("54759ab3c090d83494e2d804"));

        StepVerifier
                .create(product)
                .assertNext(prod -> {
                    assertNotNull(prod);
                    assertThat(prod.get_id(), is(equalTo(expectedId)));
                })
                .expectComplete()
                .verify();
    }
}
