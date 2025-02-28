package ru.libraryservice.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.libraryservice.libraryservice.dto.LibraryResponse;
import ru.libraryservice.libraryservice.service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/libraries")
@AllArgsConstructor
public class LibraryController {
    final private LibraryService libraryService;

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponse> getLibraryById(@PathVariable Long id) {
        LibraryResponse response = libraryService.getLibrary(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LibraryResponse>> getAllLibraries() {
        return ResponseEntity.ok(libraryService.getLibraries());
    }
}
