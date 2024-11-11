package fit.se.exceptions;

import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@ControllerAdvice
public class GlobalHandlerException {
   private final Logger logger = Logger.getLogger(GlobalHandlerException.class.getName());

   @ExceptionHandler(AppException.class)
   public ModelAndView handleAppException(AppException e) {
      logger.severe(e.getMessage());
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("errors/error");
      modelAndView.addObject("message", e.getMessage());
      return modelAndView;
   }

   @ExceptionHandler(BindValidationException.class)
   public ModelAndView handleBindValidationException(BindValidationException e) {
      logger.severe(e.getMessage());
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("errors/error");
      modelAndView.addObject("message", e.getMessage());
      return modelAndView;
   }

   @ExceptionHandler(Exception.class)
   public ModelAndView handleException(Exception e) {
      logger.severe(e.getMessage());
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("errors/error");
      modelAndView.addObject("message", e.getMessage());
      return modelAndView;
   }
}
