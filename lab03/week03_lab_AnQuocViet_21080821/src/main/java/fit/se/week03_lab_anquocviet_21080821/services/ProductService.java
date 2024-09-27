package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.ModelDtoConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.repositories.ProductRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
@Stateless
public class ProductService {
   @EJB
   private ProductRepository productRepository;


   public Set<ProductDto> getAllProducts() {
      return productRepository
                   .findAll().stream()
                   .map(p -> ModelDtoConverter.convertToDto(p, ProductDto.class))
                   .collect(Collectors.toSet());
   }

   public ProductDto getProductById(long id) {
      return productRepository
                   .findById(id)
                   .map(p -> ModelDtoConverter.convertToDto(p, ProductDto.class))
                   .orElseThrow(() -> new EntityNotFoundException("Product not found"));
   }

   public ProductDto createProduct(ProductDto productDto) {
      if (productDto == null) {
         throw new IllegalArgumentException("Product cannot be null");
      }
      productRepository.findById(productDto.id()).ifPresent(p -> {
         throw new IllegalStateException("Product already exists");
      });
      productRepository.create(ModelDtoConverter.convertToModel(productDto, Product.class));
      return productDto;
   }

   public ProductDto updateProduct(ProductDto productDto) {
      if (productDto == null) {
         throw new IllegalArgumentException("Product cannot be null");
      }
      productRepository.findById(productDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Product not found"));
      productRepository.update(ModelDtoConverter.convertToModel(productDto, Product.class));
      return productDto;
   }

   public void deleteProduct(int id) {
      boolean delete = productRepository.delete(id);
      if (!delete) {
         throw new EntityNotFoundException("Product not found");
      }
   }

   public Set<ProductDto> getProductsByIds(Set<Long> ids) {
      return productRepository.findByIds(ids).stream()
                   .map(p -> ModelDtoConverter.convertToDto(p, ProductDto.class))
                   .collect(Collectors.toSet());
   }

}
