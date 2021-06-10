package org.twitter.api.exceptions;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.twitter.service.problem.InvalidParams;
import org.twitter.service.problem.ProblemDetail;
import org.twitter.service.problem.ProblemException;

/**
 * @author someshkumarr
 * <p>
 * Global exception handler for the service.
 * All exceptions thrown by different parts of service will be handles in this class and converted to
 * proper ProblemDetail Response according to RFC - 7807
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProblemException.class})
    public ResponseEntity<Object> handleProblemException(ProblemException ex, WebRequest request) {
        ProblemDetail problem = ex.problem();
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.valueOf(problem.getStatus());
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemDetail problemDetail = new ProblemDetail.Builder()
            .status(status)
            .detail(ex)
            .build();
    log.error("Server error : {}", problemDetail);
    return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
  }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
      status = HttpStatus.BAD_REQUEST;
      List<InvalidParams> errors = new ArrayList<>();

      for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        errors.add(InvalidParams.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build());

      }

      ProblemDetail problemDetail = new ProblemDetail.Builder()
              .status(status)
              .detail(ex)
              .invalidParams(errors)
              .build();

      log.error("Validation error : {}", problemDetail);

      return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

}
