package cl.falabella.product.controllers;

import cl.falabella.product.dtos.ProductDto;
import cl.falabella.product.dtos.ProductNewDto;
import cl.falabella.product.dtos.ProductUpdateDto;
import cl.falabella.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Product", description = "Product Apis")
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/{sku}")
    public ResponseEntity<ProductDto> get(@PathVariable String sku) {
        log.debug("getSku {}", sku);
        return new ResponseEntity<>(productService.getBySku(sku), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getList() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody() ProductNewDto request) {
        productService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody() ProductUpdateDto request) {
        productService.update(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{sku}")
    public ResponseEntity<Void> delete(@PathVariable String sku) {
        productService.delete(sku);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
