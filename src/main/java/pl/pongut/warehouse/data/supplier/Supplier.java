package pl.pongut.warehouse.data.supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "suppliers")
@TypeAlias("supplier")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    private ObjectId _id;
    private String companyName;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String homePage;
}
