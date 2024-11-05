package fit.se.week03_lab_anquocviet_21080821.converters;

import fit.se.week03_lab_anquocviet_21080821.enums.EmployeeStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
@Converter(autoApply = true)
public class EmployeeStatusConverter implements AttributeConverter<EmployeeStatus, Integer> {
   @Override
   public Integer convertToDatabaseColumn(EmployeeStatus attribute) {
      return attribute.getValue();
   }

   @Override
   public EmployeeStatus convertToEntityAttribute(Integer dbData) {
      return EmployeeStatus.fromValue(dbData);
   }
}
