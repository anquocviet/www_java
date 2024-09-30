package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.dtos.ProductDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductImageDto;
import fit.se.week03_lab_anquocviet_21080821.dtos.ProductPriceDto;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;
import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;

import java.util.Comparator;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
public class ProductConverter {
   private ProductConverter() {
   }

   public static ProductDto convertToDto(Product product) {
      return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getUnit(),
            product.getManufacturer(),
            product.getStatus(),
            product.getPrices().stream()
                  .max(Comparator.comparing(ProductPrice::getPriceDateTime))
                  .map(ProductConverter::convertToPriceDto)
                  .orElse(null),
            product.getImages().stream()
                  .max(Comparator.comparing(ProductImage::getId))
                  .map(ProductConverter::convertToImageDto)
                  .orElse(null)
      );
   }

   public static Product convertToModel(ProductDto productDto) {
      Product product = new Product();
      product.setId(productDto.id());
      product.setName(productDto.name());
      product.setDescription(productDto.description());
      product.setUnit(productDto.unit());
      product.setManufacturer(productDto.manufacturer());
      product.setStatus(productDto.status());
      return product;
   }

   public static ProductPriceDto convertToPriceDto(ProductPrice productPrice) {
      return new ProductPriceDto(
            productPrice.getPriceDateTime(),
            productPrice.getPrice(),
            productPrice.getNote()
      );
   }

   public static ProductPrice convertToPriceModel(ProductPriceDto productPriceDto) {
      ProductPrice productPrice = new ProductPrice();
      productPrice.setPriceDateTime(productPriceDto.priceDateTime());
      productPrice.setPrice(productPriceDto.price());
      productPrice.setNote(productPriceDto.note());
      return productPrice;
   }

   public static ProductImageDto convertToImageDto(ProductImage productImage) {
      return new ProductImageDto(
            productImage.getPath(),
            productImage.getAlternative()
      );
   }

   public static ProductImage convertToImageModel(ProductImageDto productImageDto) {
      ProductImage productImage = new ProductImage();
      productImage.setPath(productImageDto.path());
      productImage.setAlternative(productImageDto.alternative());
      return productImage;
   }
}
