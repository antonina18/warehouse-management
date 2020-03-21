package pl.pongut.warehouse.data.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pongut.warehouse.data.supplier.Supplier;

import java.math.BigDecimal;

@Document(collection = "products")
@TypeAlias("product")
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    @Id
    private ObjectId _id;
    private String productName;
    private Integer quantityPerUnit;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private Boolean discount;
    private String categoryName;

    private Supplier supplier;

}
