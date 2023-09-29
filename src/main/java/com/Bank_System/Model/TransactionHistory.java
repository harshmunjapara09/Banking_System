package com.Bank_System.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long transactionId;

    String name;
    String accountNumber;
    String ifscCode;
    String branchName;
    Double amount;
    String status;

    String formattedTime;
    Long time;

    @PrePersist
    private void prePersist() {
        this.time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your desired date/time format
        this.formattedTime = sdf.format(new Date(this.time));
    }
}
