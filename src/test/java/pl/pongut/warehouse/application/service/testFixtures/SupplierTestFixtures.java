package pl.pongut.warehouse.application.service.testFixtures;

import org.bson.types.ObjectId;
import pl.pongut.warehouse.data.supplier.Supplier;

public class SupplierTestFixtures {
    public static final String DEFAULT_ID = "111111111111111111111111";
    public static final String DEFAULT_COMPANY_NAME = "Test Company sp. z o.o.";
    public static final String DEFAULT_CITY = "Krakow";
    public static final String DEFAULT_REGION = "Malopolska";
    public static final String DEFAULT_POSTAL_CODE = "11-111";
    public static final String DEFAULT_COUNTRY = "Poland";
    public static final String DEFAULT_PHONE = "123-123-123";
    public static final String DEFAULT_PAGE = "www.some.page.com";

    public static Supplier testSupplier(){
        return Supplier.builder()
                ._id(new ObjectId(DEFAULT_ID))
                .companyName(DEFAULT_COMPANY_NAME)
                .city(DEFAULT_CITY)
                .region(DEFAULT_REGION)
                .postalCode(DEFAULT_POSTAL_CODE)
                .country(DEFAULT_COUNTRY)
                .phone(DEFAULT_PHONE)
                .homePage(DEFAULT_PAGE)
                .build();
    }
}
