package pl.pongut.warehouse.domain.repository;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pongut.warehouse.data.product.Product;
import pl.pongut.warehouse.data.supplier.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    public static final String PRODUCT_ID_1 = "54759eb3c090d83494e2d804";
    private final Product first = Product.builder()
            ._id(new ObjectId(PRODUCT_ID_1))
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
    @Autowired
    private ProductRepository productRepository;

//    @Test
//    public void shouldReturnOneProductWithExpected2() {
//        Flux<Product> setup = productRepository.deleteAll().thenMany(productRepository.saveAll(Flux.just(first, second)));
//
//        Flux<Product> found = productRepository.findAll();
////        Flux<Product> composite = Flux.from(setup)
////                .thenMany(found);
//
//        StepVerifier
//                .create(found)
//                .expectNext(first)
//                .expectNext(second)
//                .verifyComplete();
//
//    }

    @Test
    public void shouldReturnOneProductWithExpected() {
        //given
        productRepository.save(first).block();

        //when
        Mono<Product> product = productRepository.findById(PRODUCT_ID_1);
        productRepository.findAll();
        //then
        StepVerifier
                .create(product)
                .assertNext(prod -> {
                    assertNotNull(prod);
                    assertThat(prod.get_id().toString(), is(equalTo(PRODUCT_ID_1)));
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void searchByExample() {
        //given
        productRepository.save(first).block();

        //when
        Flux<Product> productsFound = productRepository.findAll(Example.of(
                Product.builder()
                        .productName("milk")
                        .build()
        ));
        productRepository.findAll();
        //then
        StepVerifier
                .create(productsFound)
                .assertNext(prod -> {
                    assertNotNull(prod);
                    assertThat(prod.get_id().toString(), is(equalTo(PRODUCT_ID_1)));
                })
                .expectComplete()
                .verify();
    }


//    https://github.com/spring-projects/spring-data-examples/blob/master/mongodb/querydsl/src/test/java/example/springdata/mongodb/reactive/ReactiveCustomerRepositoryTests.java//    @Autowired ReactiveCustomerQuerydslRepository repository;
//    @Autowired MongoOperations operations;
//
//    Customer dave, oliver, carter;
//
//    @Before
//    public void setUp() {
//
//        repository.deleteAll().as(StepVerifier::create).verifyComplete();
//
//        dave = new Customer("Dave", "Matthews");
//        oliver = new Customer("Oliver August", "Matthews");
//        carter = new Customer("Carter", "Beauford");
//
//        repository.saveAll(Arrays.asList(dave, oliver, carter)).then().as(StepVerifier::create).verifyComplete();
//    }
//
//    @Test
//    public void findAllByPredicate() {
//
//        repository.findAll(QCustomer.customer.lastname.eq("Matthews")) //
//                .collectList() //
//                .as(StepVerifier::create) //
//                .assertNext(it -> assertThat(it).containsExactlyInAnyOrder(dave, oliver)) //
//                .verifyComplete();
//    }
}
