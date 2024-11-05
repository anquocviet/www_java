package fit.se.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
   private final int code;
   private final String message;

}
