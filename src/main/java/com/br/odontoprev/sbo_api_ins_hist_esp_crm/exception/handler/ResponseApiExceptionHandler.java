package com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception.ErrorDetail;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(DataIntegrityViolationException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleResourceException(Exception ex) {

        ErrorDetail error = ErrorDetail.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> erros = new HashMap<>();
        fieldErrors.forEach(fe -> erros.put(fe.getField(), fe.getDefaultMessage()));

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(exception.getMessage(), status);
    }
    
}
