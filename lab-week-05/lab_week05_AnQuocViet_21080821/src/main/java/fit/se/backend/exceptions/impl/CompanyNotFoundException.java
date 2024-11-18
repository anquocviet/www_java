package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class CompanyNotFoundException extends AppException {
   public CompanyNotFoundException() {
      super(404, "Công ty không tồn tại");
   }
}
