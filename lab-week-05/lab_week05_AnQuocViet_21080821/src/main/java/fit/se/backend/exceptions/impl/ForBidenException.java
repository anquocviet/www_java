package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 26/11/24
 */
public class ForBidenException extends AppException {
   public ForBidenException() {
      super(403, "Bạn không có quyền truy cập tài nguyên này.");
   }
}
