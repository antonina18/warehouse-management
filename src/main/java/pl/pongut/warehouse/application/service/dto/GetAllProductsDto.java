package pl.pongut.warehouse.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAllProductsDto {
    private String productName;
    private String categoryName;
    private Double priceFrom;
    private Double priceTo;
}
