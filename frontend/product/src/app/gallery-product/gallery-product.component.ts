import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";
import {first} from "rxjs";
import {Product} from "../model/product";

@Component({
  selector: 'app-gallery-product',
  templateUrl: './gallery-product.component.html',
  styleUrls: ['./gallery-product.component.scss']
})
export class GalleryProductComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAll()
      .pipe(first())
      .subscribe(products => this.products = products);
  }

}
