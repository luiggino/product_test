package cl.falabella.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNewDto extends ProductGenericDto {
    @URL(protocol = "https")
    @NotNull
    private String principalImage;

    private List<String> OtherImages;
}
