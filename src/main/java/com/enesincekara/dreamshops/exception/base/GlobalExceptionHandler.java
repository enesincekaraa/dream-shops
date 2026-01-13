package com.enesincekara.dreamshops.exception.base;

import com.enesincekara.dreamshops.exception.image.ImageNotFoundException;
import com.enesincekara.dreamshops.exception.image.ImageStorageException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import jakarta.persistence.OptimisticLockException;
import com.enesincekara.dreamshops.exception.category.CategoryNotFoundException;
import com.enesincekara.dreamshops.exception.product.ProductNotFoundException;
import com.enesincekara.dreamshops.response.errors.ErrorResponse;
import com.enesincekara.dreamshops.response.errors.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(ProductNotFoundException e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFound(CategoryNotFoundException e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleImageNotFound(ImageNotFoundException e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return new ValidationErrorResponse(
                "Validation failed",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
    }




    @ExceptionHandler({
            OptimisticLockException.class,
            ObjectOptimisticLockingFailureException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLock(Exception ex) {
        return new ErrorResponse(
                "The product was updated by another transaction. Please retry.",
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(ImageStorageException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleImageStorage(ImageStorageException e) {
        return new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_GATEWAY.value(),
                LocalDateTime.now()
        );
    }
}
