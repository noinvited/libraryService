package ru.libraryservice.libraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadLibraryRequestResponse {
    private String message;
    private Long id;
}
