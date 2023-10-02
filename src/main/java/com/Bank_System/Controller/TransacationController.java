package com.Bank_System.Controller;

import com.Bank_System.Model.Bank;
import com.Bank_System.Model.TransactionHistory;
import com.Bank_System.Service.EmailService;
import com.Bank_System.Service.TranscationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("HarshBank/Statement")
public class TransacationController {
    @Autowired
    TranscationService transcationService;

    @Autowired
    EmailService emailService;

    @PostMapping("/add")
    public TransactionHistory add(TransactionHistory transactionHistory){
        return transcationService.add(transactionHistory);
    }

    @GetMapping("/passbook")
    public List<TransactionHistory> getAll(@RequestParam Long accountNumber, String email){
        List<TransactionHistory> list = transcationService.getAll(accountNumber);
        emailService.sendTransactionHistoryEmail(email,list);
        return list;
    }


}
