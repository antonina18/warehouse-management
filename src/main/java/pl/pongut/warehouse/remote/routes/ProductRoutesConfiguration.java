package pl.pongut.warehouse.remote.routes;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.pongut.warehouse.application.handlers.ProductHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Getter
public class ProductRoutesConfiguration {

    private final ProductHandler productHandler;

    public ProductRoutesConfiguration(ProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> productRouterFunction(ProductHandler handler) {
        return nest(path("/products"),
                route(
                        GET("")
                                .and(accept(APPLICATION_JSON)),
                        handler::handleGetAll)
                .andRoute(
                        GET("/{id}")
                                .and(accept(APPLICATION_JSON)),
                        handler::handleGet)
               );
    }

}
