package com.Bank_System.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    private String userEmail;
    private String userAccountNumber;
    private String status;
    private String operation;
    private String otp;

}
