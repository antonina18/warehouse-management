package pl.pongut.warehouse.application;

import org.springframework.stereotype.Component;
import pl.pongut.warehouse.data.supplier.Supplier;
import pl.pongut.warehouse.domain.repository.ProductRepository;
import pl.pongut.warehouse.domain.repository.SupplierRepository;
import reactor.core.publisher.Flux;

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
        Supplier supplier = createSupplier("www.supersprzedawca.pl", "+48512022033", "PL", "31-695", "małopolska", "Kraków", "Zamki z piasku");
        Supplier supplier2 = createSupplier("www.hugoboss.pl", "+44512031033", "FR", "800-855", "La Defense", "Paryż", "Bonjour");
        Supplier supplier3 = createSupplier("www.gawarin.pl", "+41512022033", "UA", "36-458", "ukraine", "Odessa", "Panimaje");
        List<Supplier> toSave = Arrays.asList(supplier, supplier2, supplier3);
        Flux<Supplier> supplierFlux = supplierRepository.saveAll(toSave);
        System.out.println("bonjourfdsfdsfdsfdsfdsfdsfds");
        supplierFlux.doOnError(
                e -> System.out.println(e.getCause())
        ).subscribe();
        System.out.println("jk");
        Flux<Supplier> bonjour = supplierRepository.findByCompanyName("Bonjour");
        System.out.println(bonjour);
        System.out.println("bonjourfdsfdsfdsfdsfdsfdsfds");
    }

    private Supplier createSupplier(String homePage, String phone, String country, String postalCode, String region, String city, String companyName) {
        return Supplier.builder()
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
