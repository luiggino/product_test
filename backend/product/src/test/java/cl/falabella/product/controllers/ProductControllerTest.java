package cl.falabella.product.controllers;

import cl.falabella.product.domain.Product;
import cl.falabella.product.dtos.ProductDto;
import cl.falabella.product.dtos.ProductNewDto;
import cl.falabella.product.dtos.ProductUpdateDto;
import cl.falabella.product.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_thenReturnJsonObjectAndStatus200() throws Exception {
        var sku = "FAL-8406270";
        var response = getListProduct().get(0);

        when(this.productService.getBySku(sku))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products/{sku}", sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(response.getSku()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.brand").value(response.getBrand()))
                .andExpect(jsonPath("$.size").value(response.getSize()))
                .andExpect(jsonPath("$.price").value(response.getPrice()))
                .andExpect(jsonPath("$.images.[0]").value(response.getImages().get(0)));
    }

    @Test
    void getList_thenReturnJsonArrayAndStatus200() throws Exception {
        var response = getListProduct();

        when(this.productService.getAll())
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value(response.get(0).getSku()))
                .andExpect(jsonPath("$[1].sku").value(response.get(1).getSku()))
                .andExpect(jsonPath("$[2].sku").value(response.get(2).getSku()));
    }

    @Test
    void create_thenReturnStatus201() throws Exception {
        var request = new ProductNewDto();
        request.setName("500 Zapatilla Urbana Mujer");
        request.setBrand("NEW BALANCE");
        request.setSize("37");
        request.setPrice(42990.00F);
        request.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        doNothing().when(this.productService).create(request);

        var requestJson = dtoToString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void update_thenReturnStatus200() throws Exception {
        var request = new ProductUpdateDto();
        request.setSku("FAL-8406270");
        request.setName("500 Zapatilla Urbana Mujer");
        request.setBrand("NEW BALANCE");
        request.setSize("37");
        request.setPrice(42990.00F);
        request.setPrincipalImage("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        doNothing().when(this.productService).update(request);

        var requestJson = dtoToString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void delete_thenReturnStatus200() throws Exception {
        var sku = "FAL-8406270";

        doNothing().when(this.productService).delete(sku);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/products/{sku}", sku)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    List<ProductDto> getListProduct() {
        var list = new ArrayList<ProductDto>();

        var p1 = new ProductDto();
        p1.setSku("FAL-8406270");
        p1.setName("500 Zapatilla Urbana Mujer");
        p1.setBrand("NEW BALANCE");
        p1.setSize("37");
        p1.setPrice(42990.00F);
        p1.setImages(Arrays.asList("https://falabella.scene7.com/is/image/Falabella/8406270_1"));

        list.add(p1);

        var p2 = new ProductDto();
        p2.setSku("FAL-881952283");
        p2.setName("Bicicleta Baltoro Aro 29");
        p2.setBrand("JEEP");
        p2.setSize("ST");
        p2.setPrice(399990.00F);
        p1.setImages(Arrays.asList(
                "https://falabella.scene7.com/is/image/Falabella/881952283_1",
                "https://falabella.scene7.com/is/image/Falabella/881952283_2"));

        list.add(p2);

        var p3 = new ProductDto();
        p3.setSku("FAL-881898502");
        p3.setName("Camisa Manga Corta Hombre");
        p3.setBrand("BASEMENT");
        p3.setSize("M");
        p3.setPrice(24990.00F);
        p1.setImages(Arrays.asList(
                "https://falabella.scene7.com/is/image/Falabella/881898502_1"));

        list.add(p3);

        return list;
    }

    private <T> String dtoToString(T dto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(dto);
        } catch (JsonProcessingException e) {}
        return null;
    }
}
