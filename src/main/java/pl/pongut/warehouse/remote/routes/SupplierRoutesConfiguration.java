package pl.pongut.warehouse.remote.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.pongut.warehouse.application.handlers.ProductHandler;
import pl.pongut.warehouse.application.handlers.SupplierHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SupplierRoutesConfiguration {

    private SupplierHandler supplierHandler;

    public SupplierRoutesConfiguration(SupplierHandler supplierHandler) {
        this.supplierHandler = supplierHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> supplierRouterFunction(SupplierHandler supplierHandler) {
        return nest(path("/suppliers"),
                route(
                        GET("")
                                .and(accept(APPLICATION_JSON)),
                        supplierHandler::handleGetAll)
                .andRoute(
                        GET("/{id}")
                                .and(accept(APPLICATION_JSON)),
                        supplierHandler::handleGet)
               );
    }

}
