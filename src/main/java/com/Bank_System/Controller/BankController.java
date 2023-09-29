package com.Bank_System.Controller;

import com.Bank_System.Model.Bank;
import com.Bank_System.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("HarshBank")
public class BankController {

    @Autowired
    BankService bankService;
    @PostMapping("/open")
    public List<Bank> openingAccount(@RequestBody List<Bank> list){
        return bankService.openingAccount(list);
    }

    @PostMapping("/deposite")
    public Bank depositeAmount(@RequestParam String accountNumber, Double amount){
        return bankService.FindByAccountNumberforDeposite(accountNumber,amount);
    }

    @PostMapping("/withdraw")
    public Bank withdrawAmount(@RequestParam String accountNumber, Double amount){
        return bankService.FindByAccountNumberforWithdraw(accountNumber,amount);
    }

    @PostMapping("/transfer")
    public List<Bank> transferAmount(@RequestParam String senderAccount,@RequestParam String receiverAccount,@RequestParam Double amount){
        return bankService.transferAmount(senderAccount,receiverAccount,amount);
    }

    @GetMapping("/checkBalance")
    public Bank checkalance(@RequestParam String accountNumber){
        return bankService.checkalance(accountNumber);
    }
}
