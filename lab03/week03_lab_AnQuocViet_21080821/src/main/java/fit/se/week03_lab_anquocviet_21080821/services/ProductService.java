package fit.se.week03_lab_anquocviet_21080821.services;

import fit.se.week03_lab_anquocviet_21080821.converters.ProductConverter;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductImageDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductPriceDto;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;
import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;
import fit.se.week03_lab_anquocviet_21080821.repositories.ProductRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
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
   @Inject
   private ProductRepository productRepository;


   public Set<ProductDto> getAllProducts() {
      return productRepository.findAll().stream()
                   .map(ProductConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public ProductDto getProductById(long id) {
      return productRepository
                   .findById(id)
                   .map(ProductConverter::convertToDto)
                   .orElseThrow(() -> new EntityNotFoundException("Product not found"));
   }

   public ProductDto createProduct(ProductDto productDto) {
      if (productDto == null) {
         throw new IllegalArgumentException("Product cannot be null");
      }
      productRepository.findById(productDto.id()).ifPresent(p -> {
         throw new IllegalStateException("Product already exists");
      });
      Product product = ProductConverter.convertToModel(productDto);
      ProductPrice price = ProductConverter.convertToPriceModel(productDto.price());
      price.setProduct(product);
      ProductImage image = ProductConverter.convertToImageModel(productDto.image());
      image.setProduct(product);
      product.setPrices(Set.of(price));
      product.setImages(Set.of(image));
      productRepository.create(product);
      return productDto;
   }

   public ProductDto updateProduct(ProductDto productDto) {
      if (productDto == null) {
         throw new IllegalArgumentException("Product cannot be null");
      }
      productRepository.findById(productDto.id()).orElseThrow(
            () -> new EntityNotFoundException("Product not found"));
      productRepository.update(ProductConverter.convertToModel(productDto));
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
                   .map(ProductConverter::convertToDto)
                   .collect(Collectors.toSet());
   }

   public ProductDto updateProductPrice(long productId, ProductPriceDto price) {
      Product product = productRepository.findById(productId)
                              .orElseThrow(() -> new EntityNotFoundException("Product not found"));
      ProductPrice productPrice = ProductConverter.convertToPriceModel(price);
      productPrice.setProduct(product);
      productRepository.updatePrice(product, productPrice);
      return ProductConverter.convertToDto(product);
   }

   public ProductDto updateProductImage(long productId, ProductImageDto image) {
      Product product = productRepository.findById(productId)
                              .orElseThrow(() -> new EntityNotFoundException("Product not found"));
      ProductImage productImage = ProductConverter.convertToImageModel(image);
      productImage.setProduct(product);
      productRepository.updateImage(product, productImage);
      return ProductConverter.convertToDto(product);
   }
}
