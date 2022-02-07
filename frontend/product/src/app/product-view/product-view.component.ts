import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";
import {first} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-product-view',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.scss']
})
export class ProductViewComponent implements OnInit {
  sku = "";
  product: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService) {
  }

  ngOnInit(): void {
    this.sku = this.route.snapshot.params['sku'];

    this.productService.getBySku(this.sku)
      .pipe(first())
      .subscribe(product => this.product = product);
  }
}
