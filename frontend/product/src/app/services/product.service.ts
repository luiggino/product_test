import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Product} from "../model/product";

const baseUrl = `http://127.0.0.1:8080/falabella-backend/v1/products`;

@Injectable()
export class ProductService {
  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Product[]>(baseUrl);
  }

  getBySku(sku: string) {
    return this.http.get<Product>(`${baseUrl}/${sku}`);
  }

  update(product: any) {
    return this.http.put(`${baseUrl}`, product);
  }
}
