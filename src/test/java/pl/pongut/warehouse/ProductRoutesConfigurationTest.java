package pl.pongut.warehouse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.pongut.warehouse.remote.routes.ProductRoutesConfiguration;

public class ProductRoutesConfigurationTest {

    private WebTestClient client;
    private ProductRoutesConfiguration productRoutesConfiguration;

    @Before
    public void setUp() {
//         client = WebTestClient
//                .bindToRouterFunction()
//                .build();
    }

    @Test
    public void shouldReturnTwoProducts() {

    }
}
