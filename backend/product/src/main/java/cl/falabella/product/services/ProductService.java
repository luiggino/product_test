package cl.falabella.product.services;

import cl.falabella.product.domain.Product;
import cl.falabella.product.dtos.ProductDto;
import cl.falabella.product.dtos.ProductNewDto;
import cl.falabella.product.dtos.ProductUpdateDto;
import cl.falabella.product.ex.ProductNotFound;
import cl.falabella.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getBySku(String sku) {
        var product = this.productRepository.getBySkuAndIsDeletedIsFalse(sku)
                .orElseThrow(ProductNotFound::new);

        var productDto = new ProductDto();
        productDto.setSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setSize(product.getSize());
        productDto.setPrice(product.getPrice());

        var images = new ArrayList<String>();
        images.add(product.getPrincipalImage());
        images.addAll(product.getOtherImages());

        productDto.setImages(images);

        return productDto;
    }

    public List<ProductDto> getAll() {
        return this.productRepository.findAllByIsDeletedIsFalse()
                .stream()
                .map(x -> {
                    var productDto = new ProductDto();
                    productDto.setSku(x.getSku());
                    productDto.setName(x.getName());
                    productDto.setBrand(x.getBrand());
                    productDto.setSize(x.getSize());
                    productDto.setPrice(x.getPrice());

                    var images = new ArrayList<String>();
                    images.add(x.getPrincipalImage());
                    if (x.getOtherImages() != null && x.getOtherImages().size() > 0) {
                        images.addAll(x.getOtherImages());
                    }

                    productDto.setImages(images);

                    return productDto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void create(ProductNewDto request) {
        var product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setSize(request.getSize());
        product.setPrice(request.getPrice());
        product.setPrincipalImage(request.getPrincipalImage());
        product.setOtherImages(request.getOtherImages());

        this.productRepository.save(product);
    }

    @Transactional
    public void update(ProductUpdateDto request) {
        var product = this.productRepository.getBySkuAndIsDeletedIsFalse(request.getSku())
                .orElseThrow(ProductNotFound::new);

        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setSize(request.getSize());
        product.setPrice(request.getPrice());
        product.setPrincipalImage(request.getPrincipalImage());
        product.setOtherImages(request.getOtherImages());

        this.productRepository.save(product);
    }

    @Transactional
    public void delete(String sku) {
        var product = this.productRepository.getBySkuAndIsDeletedIsFalse(sku)
                .orElseThrow(ProductNotFound::new);

        product.setIsDeleted(true);
        this.productRepository.save(product);
    }

}
