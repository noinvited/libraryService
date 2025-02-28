package ru.libraryservice.libraryservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadLibraryRequestException extends BaseException {
    private final Long id;

    public BadLibraryRequestException(String message, Long id) {
        super(HttpStatus.valueOf(400), message);
        this.id = id;
    }
}
