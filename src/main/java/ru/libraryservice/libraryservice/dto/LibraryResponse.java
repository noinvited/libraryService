package ru.libraryservice.libraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryResponse {
    private String name;
    private String location;
}
