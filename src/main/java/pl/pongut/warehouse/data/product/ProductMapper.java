package pl.pongut.warehouse.data.product;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pongut.warehouse.domain.model.product.ProductDto;

@Component
@NoArgsConstructor
public class ProductMapper {

    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                ._id(productDto.get_id())
                .productName(productDto.getProductName())
                .quantityPerUnit(productDto.getQuantityPerUnit())
                .unitPrice(productDto.getUnitPrice())
                .unitsInStock(productDto.getUnitsInStock())
                .discount(productDto.getDiscount())
                .categoryName(productDto.getCategoryName())
                .build();
    }

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                ._id(product.get_id())
                .productName(product.getProductName())
                .quantityPerUnit(product.getQuantityPerUnit())
                .unitPrice(product.getUnitPrice())
                .unitsInStock(product.getUnitsInStock())
                .discount(product.getDiscount())
                .categoryName(product.getCategoryName())
                .build();
    }

}
