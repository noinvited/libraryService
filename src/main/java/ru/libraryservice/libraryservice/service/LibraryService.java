package ru.libraryservice.libraryservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.libraryservice.libraryservice.domain.Library;
import ru.libraryservice.libraryservice.dto.LibraryResponse;
import ru.libraryservice.libraryservice.exception.BadLibraryRequestException;
import ru.libraryservice.libraryservice.repo.LibraryRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepo libraryRepo;

    /**
     * Возвращает информацию о библиотеке по её идентификатору.
     *
     * @param id идентификатор библиотеки
     * @return объект {@link LibraryResponse}, содержащий название и местоположение библиотеки
     * @throws BadLibraryRequestException если библиотека с указанным идентификатором не найдена
     */
    public LibraryResponse getLibrary(Long id) throws BadLibraryRequestException{
        Library library = getLibraryById(id);
        return new LibraryResponse(library.getName(), library.getLocation());
    }

    /**
     * Возвращает список всех библиотек в виде объектов {@link LibraryResponse}.
     *
     * @return список объектов {@link LibraryResponse}, содержащих название и местоположение каждой библиотеки
     */
    public List<LibraryResponse> getLibraries(){
        List<Library> libraries = getAllLibraries();
        return libraries.stream().map(library -> new LibraryResponse(library.getName(), library.getLocation()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает объект библиотеки из базы данных по её идентификатору.
     *
     * @param id идентификатор библиотеки
     * @return объект {@link Library}, представляющий библиотеку
     * @throws BadLibraryRequestException если библиотека с указанным идентификатором не найдена
     */
    public Library getLibraryById(Long id) throws BadLibraryRequestException{
        return libraryRepo.findById(id).orElseThrow(() -> new BadLibraryRequestException("Library not found", id));
    }

    /**
     * Возвращает список всех библиотек из базы данных.
     *
     * @return список объектов {@link Library}, представляющих все библиотеки
     */
    public List<Library> getAllLibraries() {
        return libraryRepo.findAll();
    }
}
