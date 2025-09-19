package com.example.dam.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Invite {
    @ManyToOne
    @JoinColumn(nullable = false)
    Tenant tenant;

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    User invitedBy;

    @Column(nullable = false)
    String inviteeEmail;

    @ManyToOne
    Role role;

    @ManyToOne
    Space space;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Status status = Status.PENDING;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    LocalDateTime invitedAt = LocalDateTime.now();

    @LastModifiedDate
    LocalDateTime responseAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
