package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.ProductConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.repositories.ProductRepository;
import jakarta.inject.Inject;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
public class ProductService {
   @Inject
   private ProductRepository productRepository;


   public Set<ProductDto> getAllProducts() {
      return productRepository.findAll()
                   .stream().map(ProductConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public Product getProductById(int id) {
      return productRepository.findById(id);
   }

}
