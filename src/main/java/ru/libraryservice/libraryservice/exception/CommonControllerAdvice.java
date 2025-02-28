package ru.libraryservice.libraryservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.libraryservice.libraryservice.dto.BadLibraryRequestResponse;

@ControllerAdvice(annotations = RestController.class)
public class CommonControllerAdvice {
    @ExceptionHandler(BadLibraryRequestException.class)
    public ResponseEntity<BadLibraryRequestResponse> handleInvalidRequestCategoryException(BadLibraryRequestException e) {
        return ResponseEntity.status(e.getStatus()).body(new BadLibraryRequestResponse(e.getMessage(), e.getId()));
    }
}
