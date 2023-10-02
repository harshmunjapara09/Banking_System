package com.Bank_System.Controller;

import com.Bank_System.Model.TransactionHistory;
import com.Bank_System.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

//    @PostMapping("/sendTransactionHistoryEmail")
//    public ResponseEntity<String> sendTransactionHistoryEmail(
//            @RequestParam String recipientEmail,
//            @RequestParam List<TransactionHistory> transactionHistoryList) {
//
//        if (transactionHistoryList.isEmpty()) {
//            return ResponseEntity.badRequest().body("Transaction history is empty.");
//        }
//
//        emailService.sendTransactionHistoryEmail(recipientEmail, transactionHistoryList);
//        return ResponseEntity.ok("Email sent successfully.");
//    }
}
