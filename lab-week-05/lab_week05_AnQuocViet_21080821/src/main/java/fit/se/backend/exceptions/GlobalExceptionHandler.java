package fit.se.backend.exceptions;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@ControllerAdvice
public class GlobalExceptionHandler {
   private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
   private static final String ERROR_VIEW = "error";
   private static final String ERROR_MESSAGE_ATTRIBUTE = "message";

   @ExceptionHandler(AppException.class)
   public ModelAndView handleAppException(AppException e) {
      logger.log(Level.SEVERE, "App error: ", e);
      ModelAndView mav = new ModelAndView(ERROR_VIEW);
      mav.addObject(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
      return mav;
   }

   @ExceptionHandler(BindException.class)
   public ModelAndView handleBindException(BindException e) {
      logger.log(Level.SEVERE, "Validation error: ", e);

      ModelAndView mav = new ModelAndView(ERROR_VIEW);
      mav.addObject(ERROR_MESSAGE_ATTRIBUTE, e.getAllErrors().get(0).getDefaultMessage());
      return mav;
   }

   @ExceptionHandler(NoResourceFoundException.class)
   public ModelAndView handleNoResourceFoundException(NoResourceFoundException e) {
      logger.log(Level.SEVERE, "Resource not found: ", e);

      ModelAndView mav = new ModelAndView(ERROR_VIEW);
      mav.addObject(ERROR_MESSAGE_ATTRIBUTE, "Đường dẫn không tồn tại");
      return mav;
   }

   @ExceptionHandler(HttpClientErrorException.Forbidden.class)
   public ModelAndView handle(HttpClientErrorException.Forbidden e) {
      logger.log(Level.SEVERE, "Forbidden error: ", e);

      ModelAndView mav = new ModelAndView(ERROR_VIEW);
      mav.addObject(ERROR_MESSAGE_ATTRIBUTE, "Bạn không có quyền truy cập trang này");
      return mav;
   }

   @ExceptionHandler(Exception.class)
   public ModelAndView handleUnwantedException(Exception e) {
      logger.log(Level.SEVERE, "Unknown error: ", e);

      ModelAndView mav = new ModelAndView(ERROR_VIEW);
      mav.addObject(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
      return mav;
   }
}
