package fit.se.week03_lab_anquocviet_21080821.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
   final ObjectMapper om = new ObjectMapper();

   public ObjectMapperContextResolver() {
      om.registerModule(new JavaTimeModule());
   }

   @Override
   public ObjectMapper getContext(Class<?> type) {
      return om;
   }
}
