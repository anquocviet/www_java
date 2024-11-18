package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class EmailAlreadyExistsException extends AppException {
   public EmailAlreadyExistsException() {
      super(400, "Email đã tồn tại");
   }
}
