package pl.pongut.warehouse.data.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pongut.warehouse.data.supplier.Supplier;


@Document(collection = "products")
@TypeAlias("product")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    @Id
    private ObjectId _id;
    private String productName;
    private Integer quantityPerUnit;
    private Double unitPrice;
    private Integer unitsInStock;
    private Boolean discount;
    private String categoryName;

    private Supplier supplier;

}
