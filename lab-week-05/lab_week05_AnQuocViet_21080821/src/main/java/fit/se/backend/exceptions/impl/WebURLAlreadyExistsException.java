package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class WebURLAlreadyExistsException extends AppException {
   public WebURLAlreadyExistsException() {
      super(400, "Web URL đã tồn tại");
   }
}
