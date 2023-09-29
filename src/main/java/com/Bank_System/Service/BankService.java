package com.Bank_System.Service;

import com.Bank_System.Model.Bank;
import com.Bank_System.Model.TransactionHistory;
import com.Bank_System.Repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankService {
    @Autowired
    BankRepo bankRepo;
    @Autowired
    TranscationService transcationService;


    public List<Bank> openingAccount(List<Bank> list) {
        return bankRepo.saveAll(list);
    }

    public Bank FindByAccountNumberforDeposite(String accountNumber, Double amount) {
        Bank update = bankRepo.FindByAccountNumber(accountNumber);
        Double currentAmount = update.getAmount();
        currentAmount += amount;
        update.setAccountNumber(accountNumber);
        update.setAmount(currentAmount);

        TransactionHistory th = new TransactionHistory();
        th.setBranchName(update.getBranchName());
        th.setIfscCode(update.getIfscCode());
        th.setName(update.getName());
        th.setAmount(amount);
        Long transactionId = generateUniqueTransactionId();
        th.setTransactionId(transactionId);
        th.setAccountNumber(update.getAccountNumber());
        th.setStatus("Deposite");
//        th.setTime(new Date().getTime());
        transcationService.add(th);

        return bankRepo.save(update);
    }

    public Bank FindByAccountNumberforWithdraw(String accountNumber, Double amount) {
        Bank update = bankRepo.FindByAccountNumber(accountNumber);
        if (update.getAmount() < amount) {
            return update;
        }
        Double currentAmount = update.getAmount();
        currentAmount -= amount;
        update.setAccountNumber(accountNumber);
        update.setAmount(currentAmount);

        TransactionHistory th = new TransactionHistory();
        th.setBranchName(update.getBranchName());
        th.setIfscCode(update.getIfscCode());
        th.setName(update.getName());
        th.setAmount(amount);
        Long transactionId = generateUniqueTransactionId();
        th.setTransactionId(transactionId);
        th.setAccountNumber(update.getAccountNumber());
        th.setStatus("Withdraw");
        transcationService.add(th);

        return bankRepo.save(update);
    }

    private Long generateUniqueTransactionId() {
        // Get the current timestamp
        long timestamp = new Date().getTime();

        // Generate a random number (you can use a more robust random number generator)
        Random random = new Random();
        long randomNumber = random.nextLong();

        // Combine timestamp and random number to create a unique ID
        return timestamp + randomNumber;
    }


    public List<Bank> transferAmount(String senderAccount, String receiverAccount, Double amount) {
        Bank sender = bankRepo.FindByAccountNumber(senderAccount);
        Bank recevicer = bankRepo.FindByAccountNumber(receiverAccount);

        List<Bank> list = new ArrayList<>();
        list.add(sender);
        list.add(recevicer);

        Double currentAmountSender = sender.getAmount();
        if (currentAmountSender < amount) {
            return list;
        }
        currentAmountSender -= amount;
        sender.setAccountNumber(sender.getAccountNumber());
        sender.setAmount(currentAmountSender);

        TransactionHistory th = new TransactionHistory();
        th.setBranchName(sender.getBranchName());
        th.setIfscCode(sender.getIfscCode());
        th.setName(sender.getName());
        th.setAmount(amount);
        Long transactionId = generateUniqueTransactionId();
        th.setTransactionId(transactionId);
        th.setAccountNumber(sender.getAccountNumber());
        th.setStatus("Debited");
        transcationService.add(th);

        Double currentAmountRecevier = recevicer.getAmount();
        currentAmountRecevier += amount;
        recevicer.setAccountNumber(recevicer.getAccountNumber());
        recevicer.setAmount(currentAmountRecevier);

        TransactionHistory th1 = new TransactionHistory();
        th1.setBranchName(recevicer.getBranchName());
        th1.setIfscCode(recevicer.getIfscCode());
        th1.setName(recevicer.getName());
        th1.setAmount(amount);
        th1.setTransactionId(th.getTransactionId());
        th1.setAccountNumber(recevicer.getAccountNumber());
        th1.setStatus("Credited");

        transcationService.add(th1);

        bankRepo.save(recevicer);
        bankRepo.save(sender);
        return list;
    }
}
