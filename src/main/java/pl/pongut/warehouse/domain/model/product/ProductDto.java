package pl.pongut.warehouse.domain.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class ProductDto {

    private ObjectId _id;
    private String productName;
    private Integer quantityPerUnit;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private Boolean discount;
    private String categoryName;

    private ObjectId supplierId;
}
