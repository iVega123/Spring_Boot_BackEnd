package payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MedicoNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(MedicoNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String medicoNotFoundHandler(MedicoNotFoundException ex) {
    return ex.getMessage();
  }
}