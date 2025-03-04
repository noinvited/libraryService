package ru.libraryservice.libraryservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class LibraryServiceApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private String libraryByIdTestCorrectCheck = "{\"name\":\"№1\",\"location\":\"New York\"}";
    private String libraryByIdTestIncorrectCheck = "{\"message\":\"Library not found\",\"id\":20}";
    private String allLibraryTestCorrectCheck = "[{\"name\":\"№1\",\"location\":\"New York\"},{\"name\":\"№2\",\"location\":\"Los Angeles\"},{\"name\":\"№3\",\"location\":\"Chicago\"},{\"name\":\"№4\",\"location\":\"Houston\"},{\"name\":\"№5\",\"location\":\"Phoenix\"},{\"name\":\"№6\",\"location\":\"Philadelphia\"},{\"name\":\"№7\",\"location\":\"San AntonioX\"},{\"name\":\"№8\",\"location\":\"San Diego\"}]";

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    @DisplayName("Информация о библиотеке по id")
    void GetLibraryByIdTestCorrect() {
        ResponseEntity<String> response = restTemplate.getForEntity("/libraries/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(libraryByIdTestCorrectCheck, response.getBody());
    }

    @Test
    @DisplayName("Поиск несуществующей библиотеки")
    void GetLibraryByIdTestIncorrect() {
        ResponseEntity<String> response = restTemplate.getForEntity("/libraries/20", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(libraryByIdTestIncorrectCheck, response.getBody());
    }

    @Test
    @DisplayName("Информация о всех библиотеках")
    void GetAllLibraryTestCorrect() {
        ResponseEntity<String> response = restTemplate.getForEntity("/libraries", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(allLibraryTestCorrectCheck, response.getBody());
    }
}
