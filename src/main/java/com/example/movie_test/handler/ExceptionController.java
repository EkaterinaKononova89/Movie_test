package com.example.movie_test.handler;

import com.example.movie_test.dto.ErrorResponse;
import com.example.movie_test.exception.ErrorInputData;
import com.example.movie_test.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorResponse> errorInputDataHandler(ErrorInputData ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> movieNotFoundExceptionHandler(MovieNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND); // 404
    }
}
