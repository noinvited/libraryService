package ru.libraryservice.libraryservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "library")
public class Library {
    @Id
    @Column(name = "id")
    private Long id;

    private String name;
    private String location;
}
