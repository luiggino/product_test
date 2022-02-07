import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {ProductService} from "../services/product.service";
import {first} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {
  sku = "";

  validation_messages = {
    name: [
      {type: 'required', message: 'El nombre es obligatorio'},
      {type: 'minlength', message: 'El nombre debe tener al menos 3 caracteres'},
      {type: 'maxlength', message: 'El nombre no debe ser mayor a 50 caracteres'},
    ],
    brand: [
      {type: 'required', message: 'El marca es obligatorio'},
      {type: 'minlength', message: 'El marca debe tener al menos 3 caracteres'},
      {type: 'maxlength', message: 'El marca no debe ser mayor a 50 caracteres'},
    ],
    size: [
      {type: 'nullValidator', message: 'El precio no puede ser nulo'}
    ],
    price: [
      {type: 'required', message: 'El precio es obligatorio'},
      {type: 'min', message: 'El precio debe ser mínimo de 1'},
      {type: 'max', message: 'El precio no debe ser máximo de 99999999'},
    ],
    images: [
      {type: 'pattern', message: 'Las imágenes deben ser urls'},
    ],
  }

  url_reg = '(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?';

  productForm = this.fb.group({
    name: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50)]),
    brand: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50)]),
    size: new FormControl('', [Validators.nullValidator]),
    price: new FormControl('', [Validators.required,
      Validators.min(1.00),
      Validators.max(99999999.00)]),
    images: new FormControl('', [Validators.pattern(this.url_reg)])
  });

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private productService: ProductService,
    private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.sku = this.route.snapshot.params['sku'];

    this.productService.getBySku(this.sku)
      .pipe(first())
      .subscribe(product => {
        this.productForm.patchValue(product)
      });
  }

  onSubmit() {
    console.log('form data is ', this.productForm.value);

    const body = {
      name: this.productForm.get('name')?.value,
      brand: this.productForm.get('brand')?.value,
      size: this.productForm.get('size')?.value,
      price: this.productForm.get('price')?.value,
      principalImage: this.productForm.get('images')?.value[0],
      sku: this.sku,
      otherImages: [...this.productForm.get('images')?.value.shift()],
    }

    this.productService.update(body)
      .pipe(first())
      .subscribe({
        next: () => {
          this._snackBar.open("producto actualizado correctamente",
          '',
            {duration: 10 * 1000});
          this.router.navigate(['/']);
        },
        error: error => {
          this._snackBar.open("error al actualizar el producto",
            '',
            {duration: 10 * 1000});
        }
      });

  }

  public hasError = (controlName: string, errorName: string) => {
    return this.productForm.controls[controlName].hasError(errorName);
  }
}
