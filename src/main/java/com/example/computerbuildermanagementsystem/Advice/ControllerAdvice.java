package com.example.computerbuildermanagementsystem.Advice;

import com.example.computerbuildermanagementsystem.Api.ApiException;
import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice

public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> ApiException(ApiException apiException){
        String message = apiException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> HandleValidation(MethodArgumentNotValidException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getBindingResult().getFieldError().getDefaultMessage()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<?> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        return ResponseEntity.status(400).body(new ApiResponse("media type not supported"));
    }

}
