package ru.libraryservice.libraryservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.libraryservice.libraryservice.domain.Library;

public interface LibraryRepo extends JpaRepository<Library, Long> {
}
