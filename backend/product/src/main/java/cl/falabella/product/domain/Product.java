package cl.falabella.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "fal-generator")
    @GenericGenerator(name = "fal-generator",
            parameters = @Parameter(name = "prefix", value = "FAL"),
            strategy = "cl.falabella.product.domain.generator.FalGenerator")
    private String sku;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String brand;

    @Column(nullable = false)
    private String size;

    @Column
    private Float price;

    @Column(name = "pricipal_image")
    private String principalImage;

    @Column(name = "other_images")
    @ElementCollection
    private List<String> OtherImages = new ArrayList<String>();

    @Column(name = "isDeleted", columnDefinition = "boolean default true")
    private Boolean isDeleted = false;
}
