package pl.pongut.warehouse.application.service.testFixtures;

import org.bson.types.ObjectId;
import pl.pongut.warehouse.data.product.Product;

import static pl.pongut.warehouse.application.service.testFixtures.SupplierTestFixtures.testSupplier;

public class ProductTestFixtures {

    public static final String DEFAULT_CATEGORY = "some category";
    public static final boolean DEFAULT_DISCOUNT = false;
    public static final int DEFAULT_UNITS_IN_STOCK = 10;
    public static final Double DEFAULT_PRICE = 10d;
    public static final int DEFAULT_QUANTITY_PER_UNIT = 1;
    public static final String DEFAULT_PRODUCT_NAME = "some product name";

    public static Product testProduct() {
        return testProduct(new ObjectId());
    }
        public static Product testProduct(ObjectId id) {
        return Product.builder()
                ._id(id)
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
