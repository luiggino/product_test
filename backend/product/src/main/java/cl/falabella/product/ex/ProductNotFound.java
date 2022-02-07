package cl.falabella.product.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFound extends RuntimeException {
    private static final String message = "Product not Found";

    public ProductNotFound() {
        super(message);
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
