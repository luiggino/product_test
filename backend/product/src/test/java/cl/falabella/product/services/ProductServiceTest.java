package cl.falabella.product.services;

import cl.falabella.product.domain.Product;
import cl.falabella.product.dtos.ProductNewDto;
import cl.falabella.product.dtos.ProductUpdateDto;
import cl.falabella.product.ex.ProductNotFound;
import cl.falabella.product.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getBySku_thenReturnJsonObject() {
        var product = getList().get(0);

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenReturn(Optional.of(product));

        var response = productService.getBySku(product.getSku());
        assertThat(response).isNotNull();
        assertThat(response.getSku()).isEqualTo(product.getSku());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getBrand()).isEqualTo(product.getBrand());
        assertThat(response.getSize()).isEqualTo(product.getSize());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
        assertThat(response.getImages().get(0)).isEqualTo(product.getPrincipalImage());
    }

    @Test
    void getBySku_exception_NotFound() {
        var sku = "my-sku";

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenThrow(new ProductNotFound());

        assertThatExceptionOfType(ProductNotFound.class)
                .isThrownBy(() -> productService.getBySku(sku))
                .withMessage("Product not Found")
                .withNoCause();
    }

    @Test
    void geAll_thenReturnJsonArray() {
        var listProduct = getList();

        when(productRepository.findAllByIsDeletedIsFalse())
                .thenReturn(listProduct);

        var response = productService.getAll();
        assertThat(response).isNotNull();
        assertThat(response.get(0).getSku()).isEqualTo(listProduct.get(0).getSku());
        assertThat(response.get(1).getSku()).isEqualTo(listProduct.get(1).getSku());
        assertThat(response.get(2).getSku()).isEqualTo(listProduct.get(2).getSku());
    }

    @Test
    void create_thenReturn_201() {
        var request = new ProductNewDto();
        request.setName("500 Zapatilla Urbana Mujer");
        request.setBrand("NEW BALANCE");
        request.setSize("37");
        request.setPrice(42990.00F);
        request.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        var entity = new Product();
        entity.setName("500 Zapatilla Urbana Mujer");
        entity.setBrand("NEW BALANCE");
        entity.setSize("37");
        entity.setPrice(42990.00F);
        entity.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productRepository.save(any(Product.class)))
                .thenReturn(entity);

        productService.create(request);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_thenReturn_200() {
        var request = new ProductUpdateDto();
        request.setSku("FAL-8406270");
        request.setName("500 Zapatilla Urbana Mujer");
        request.setBrand("NEW BALANCE");
        request.setSize("37");
        request.setPrice(42990.00F);
        request.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        var entity = new Product();
        entity.setName("500 Zapatilla Urbana Mujer");
        entity.setBrand("NEW BALANCE");
        entity.setSize("37");
        entity.setPrice(42990.00F);
        entity.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenReturn(Optional.of(entity));
        when(productRepository.save(any(Product.class)))
                .thenReturn(entity);

        productService.update(request);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_exception_NotFound() {
        var request = new ProductUpdateDto();
        request.setSku("FAL-8406270");
        request.setName("500 Zapatilla Urbana Mujer");
        request.setBrand("NEW BALANCE");
        request.setSize("37");
        request.setPrice(42990.00F);
        request.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenThrow(new ProductNotFound());

        assertThatExceptionOfType(ProductNotFound.class)
                .isThrownBy(() -> productService.update(request))
                .withMessage("Product not Found")
                .withNoCause();
    }

    @Test
    void delete_thenReturn_200() {
        var sku = "FAL-8406270";

        var entity = new Product();
        entity.setName("500 Zapatilla Urbana Mujer");
        entity.setBrand("NEW BALANCE");
        entity.setSize("37");
        entity.setPrice(42990.00F);
        entity.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenReturn(Optional.of(entity));
        when(productRepository.save(any(Product.class)))
                .thenReturn(entity);

        productService.delete(sku);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_exception_NotFound() {
        var sku = "FAL-8406270";

        when(productRepository.getBySkuAndIsDeletedIsFalse(any()))
                .thenThrow(new ProductNotFound());

        assertThatExceptionOfType(ProductNotFound.class)
                .isThrownBy(() -> productService.delete(sku))
                .withMessage("Product not Found")
                .withNoCause();
    }

    List<Product> getList() {
        var list = new ArrayList<Product>();

        var p1 = new Product();
        p1.setSku("FAL-8406270");
        p1.setName("500 Zapatilla Urbana Mujer");
        p1.setBrand("NEW BALANCE");
        p1.setSize("37");
        p1.setPrice(42990.00F);
        p1.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        list.add(p1);

        var p2 = new Product();
        p2.setSku("FAL-881952283");
        p2.setName("Bicicleta Baltoro Aro 29");
        p2.setBrand("JEEP");
        p2.setSize("ST");
        p2.setPrice(399990.00F);
        p2.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/881952283_1");
        p2.setOtherImages(Arrays.asList("https://falabella.scene7.com/is/image/Falabella/881952283_2"));

        list.add(p2);

        var p3 = new Product();
        p3.setSku("FAL-881898502");
        p3.setName("Camisa Manga Corta Hombre");
        p3.setBrand("BASEMENT");
        p3.setSize("M");
        p3.setPrice(24990.00F);
        p3.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/881898502_1");

        list.add(p3);

        return list;
    }
}
