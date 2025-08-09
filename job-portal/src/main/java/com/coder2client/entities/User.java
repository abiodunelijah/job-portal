package com.coder2client.entities;


import com.coder2client.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required.")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email is required.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;


    @Column(name = "is_active")
    private boolean isActive = true;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
