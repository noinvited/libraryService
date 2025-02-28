package ru.libraryservice.libraryservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.libraryservice.libraryservice.domain.Library;
import ru.libraryservice.libraryservice.dto.LibraryResponse;
import ru.libraryservice.libraryservice.exception.BadLibraryRequestException;
import ru.libraryservice.libraryservice.repos.LibraryRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepo libraryRepo;

    public LibraryResponse getLibrary(Long id) {
        Library library = getLibraryById(id);
        return new LibraryResponse(library.getName(), library.getLocation());
    }

    public List<LibraryResponse> getLibraries(){
        List<Library> libraries = getAllLibraries();
        return libraries.stream().map(library -> new LibraryResponse(library.getName(), library.getLocation()))
                .collect(Collectors.toList());
    }

    public Library getLibraryById(Long id) {
        return libraryRepo.findById(id).orElseThrow(() -> new BadLibraryRequestException("Library not found", id));
    }

    public List<Library> getAllLibraries() {
        return libraryRepo.findAll();
    }
}
