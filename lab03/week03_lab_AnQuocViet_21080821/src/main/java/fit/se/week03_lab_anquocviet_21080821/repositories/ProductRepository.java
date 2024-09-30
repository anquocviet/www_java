package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;
import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;

import java.util.Set;

public interface ProductRepository extends IRepository<Product> {
   Set<Product> findByIds(Set<Long> ids);
   Product updatePrice(Product product, ProductPrice price);
   Product updateImage(Product product, ProductImage image);
}
