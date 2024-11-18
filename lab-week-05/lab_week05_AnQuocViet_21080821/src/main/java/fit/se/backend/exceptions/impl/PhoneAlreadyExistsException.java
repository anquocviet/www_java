package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class PhoneAlreadyExistsException extends AppException {
   public PhoneAlreadyExistsException() {
      super(400, "Số điện thoại đã tồn tại");
   }
}
