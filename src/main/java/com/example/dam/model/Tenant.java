package com.example.dam.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tenant {
    @Id
    @GeneratedValue
    UUID id;

    String name;

    @OneToMany(mappedBy = "tenant")
    List<Credential> credentials;

}
