package fit.se.backend.exceptions.impl;

import fit.se.backend.exceptions.AppException;

/**
 * @description
 * @author: vie
 * @date: 18/11/24
 */
public class CandidateNotFound extends AppException {
   public CandidateNotFound() {
      super(404, "Ứng viên không tồn tại");
   }
}
