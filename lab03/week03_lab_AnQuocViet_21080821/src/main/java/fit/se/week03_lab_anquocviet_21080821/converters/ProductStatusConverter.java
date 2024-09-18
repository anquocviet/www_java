package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.enums.ProductStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, Integer> {
   @Override
   public Integer convertToDatabaseColumn(ProductStatus attribute) {
      if (attribute == null) {
         return null;
      }
      return attribute.getValue();
   }

   @Override
   public ProductStatus convertToEntityAttribute(Integer dbData) {
      if (dbData == null) {
         return null;
      }
      return ProductStatus.fromValue(dbData);
   }
}
