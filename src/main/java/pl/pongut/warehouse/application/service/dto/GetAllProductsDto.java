package pl.pongut.warehouse.application.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") //TODO: is it good idea?
@Builder
@Getter
public class GetAllProductsDto {
    private Optional<String> productName;
    private Optional<String> categoryName;
    private Optional<BigDecimal> priceFrom;
    private Optional<BigDecimal> priceTo;
}
