package pl.pongut.warehouse.data.supplier;

import org.springframework.stereotype.Component;
import pl.pongut.warehouse.domain.model.supplier.SupplierDto;

@Component
public class SupplierMapper {

    public Supplier mapToSupplier(SupplierDto supplierDto) {
        return Supplier.builder()
                ._id(supplierDto.get_id())
                .companyName(supplierDto.getCompanyName())
                .city(supplierDto.getCity())
                .region(supplierDto.getRegion())
                .postalCode(supplierDto.getPostalCode())
                .country(supplierDto.getCountry())
                .phone(supplierDto.getPhone())
                .homePage(supplierDto.getHomePage())
                .build();
    }

    public SupplierDto mapToSupplierDto(Supplier supplier) {
        return SupplierDto.builder()
                ._id(supplier.get_id())
                .companyName(supplier.getCompanyName())
                .city(supplier.getCity())
                .region(supplier.getRegion())
                .postalCode(supplier.getPostalCode())
                .country(supplier.getCountry())
                .phone(supplier.getPhone())
                .homePage(supplier.getHomePage())
                .build();
    }
}
