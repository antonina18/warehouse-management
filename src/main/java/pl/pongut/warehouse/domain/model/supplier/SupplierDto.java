package pl.pongut.warehouse.domain.model.supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@Getter
public class SupplierDto {

    private ObjectId _id;
    private String companyName;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String homePage;
}
