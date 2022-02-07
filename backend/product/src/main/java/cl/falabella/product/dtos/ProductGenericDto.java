package cl.falabella.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGenericDto {
    @NotBlank(message = "The name is required.")
    @Size(min = 3, max = 50, message = "The length of name must be between 3 and 50 characters.")
    private String name;

    @NotBlank(message = "The brand is required.")
    @Size(min = 3, max = 50, message = "The length of brand must be between 3 and 50 characters.")
    private String brand;

    @NotBlank(message = "The size is not blank.")
    private String size;

    @Min(1)
    @Max(99999999)
    @NotNull
    private Float price;
}
