package ru.libraryservice.libraryservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class LibraryServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

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
    void GetLibraryByIdTestCorrect() throws Exception {
        mockMvc.perform(get("/libraries/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                        {
											"name": "№1",
											"location": "New York"
										}
                                        """)
                );
    }

    @Test
    void GetLibraryByIdTestIncorrect() throws Exception {
        mockMvc.perform(get("/libraries/20"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                        {
                                            "message": "Library not found",
                                            "id": 20
                                        }                                       
                                        """)
                );
    }

    @Test
    void GetAllLibraryTestCorrect() throws Exception {
        mockMvc.perform(get("/libraries"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                        [
                                            {
                                                "name": "№1",
                                                "location": "New York"
                                            },
                                            {
                                                "name": "№2",
                                                "location": "Los Angeles"
                                            },
                                            {
                                                "name": "№3",
                                                "location": "Chicago"
                                            },
                                            {
                                                "name": "№4",
                                                "location": "Houston"
                                            },
                                            {
                                                "name": "№5",
                                                "location": "Phoenix"
                                            },
                                            {
                                                "name": "№6",
                                                "location": "Philadelphia"
                                            },
                                            {
                                                "name": "№7",
                                                "location": "San AntonioX"
                                            },
                                            {
                                                "name": "№8",
                                                "location": "San Diego"
                                            }
                                        ]
                                        """)
                );
    }
}
