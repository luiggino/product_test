package cl.falabella.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends ProductGenericDto {
    @NotBlank(message = "The sku is required.")
    @Pattern(regexp = "FAL-[0-9]{7,8}", message = "The sku format is wrong.")
    private String sku;

    private List<String> images;
}
