import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from "./not-found/not-found.component";
import {GalleryProductComponent} from "./gallery-product/gallery-product.component";
import {ProductViewComponent} from "./product-view/product-view.component";
import {ProductEditComponent} from "./product-edit/product-edit.component";

const routes: Routes = [
  { path: '', component: GalleryProductComponent },
  { path: 'product/:sku', component: ProductViewComponent },
  { path: 'product-edit/:sku', component: ProductEditComponent },
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
