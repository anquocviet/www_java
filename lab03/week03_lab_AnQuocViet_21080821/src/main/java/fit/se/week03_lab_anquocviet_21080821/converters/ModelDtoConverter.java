package fit.se.week03_lab_anquocviet_21080821.converters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
public class ModelDtoConverter {
   private static final ObjectMapper om =
         new ObjectMapper()
               .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // ignore unknown properties
               .registerModule(new JavaTimeModule()); // support for Java 8 Date/Time API

   public static <T> T convertToDto(Object model, Class<T> dtoClass) {
      return om.convertValue(model, dtoClass);
   }

   public static <T> T convertToModel(Object dto, Class<T> modelClass) {
      return om.convertValue(dto, modelClass);
   }
}
