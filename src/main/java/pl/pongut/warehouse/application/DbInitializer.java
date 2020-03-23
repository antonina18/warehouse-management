package pl.pongut.warehouse.application;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import pl.pongut.warehouse.data.supplier.Supplier;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import pl.pongut.warehouse.domain.repository.SupplierRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class DbInitializer {

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;

    public DbInitializer(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @PostConstruct
    public void initialize() {
        List<Supplier> toSave = Arrays.asList(
                createSupplier("111111111111111111111111", "www.supersprzedawca.pl", "+48512022033", "PL", "31-695", "małopolska", "Kraków", "Zamki z piasku"),
                createSupplier("222222222222222222222222", "www.hugoboss.pl", "+44512031033", "FR", "800-855", "La Defense", "Paryż", "Bonjour"),
                createSupplier("333333333333333333333333", "www.gawarin.pl", "+41512022033", "UA", "36-458", "ukraine", "Odessa", "Panimaje")
        );
        supplierRepository.saveAll(toSave).subscribe();
    }

    private Supplier createSupplier(String hexId, String homePage, String phone, String country, String postalCode, String region, String city, String companyName) {
        return Supplier.builder()
                ._id(new ObjectId(hexId))
                .homePage(homePage)
                .phone(phone)
                .country(country)
                .postalCode(postalCode)
                .region(region)
                .city(city)
                .companyName(companyName)
                .build();
    }
}
