package pl.pongut.warehouse.application.service.testFixtures;

import org.bson.types.ObjectId;
import pl.pongut.warehouse.data.product.Product;

import java.math.BigDecimal;
import java.util.UUID;

import static pl.pongut.warehouse.application.service.testFixtures.SupplierTestFixtures.testSupplier;

public class ProductTestFixtures {

    public static final String DEFAULT_CATEGORY = "some category";
    public static final boolean DEFAULT_DISCOUNT = false;
    public static final int DEFAULT_UNITS_IN_STOCK = 10;
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.TEN;
    public static final int DEFAULT_QUANTITY_PER_UNIT = 1;
    public static final String DEFAULT_PRODUCT_NAME = "some product name";

    public static Product testProduct() {
        return testProduct(UUID.randomUUID().toString());
    }
        public static Product testProduct(String id) {
        return Product.builder()
                ._id(new ObjectId(id))
                .categoryName(DEFAULT_CATEGORY)
                .discount(DEFAULT_DISCOUNT)
                .unitsInStock(DEFAULT_UNITS_IN_STOCK)
                .unitPrice(DEFAULT_PRICE)
                .quantityPerUnit(DEFAULT_QUANTITY_PER_UNIT)
                .productName(DEFAULT_PRODUCT_NAME)
                .supplier(testSupplier())
                .build();
    }
}
