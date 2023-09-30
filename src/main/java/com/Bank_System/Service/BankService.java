package com.Bank_System.Service;

import com.Bank_System.Model.Bank;
import com.Bank_System.Model.ClosedAccount;
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
    @Autowired
    ClosedAccountService closedAccountService;


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
        long timestamp = new Date().getTime();

        Random random = new Random();
        long randomNumber = random.nextLong();

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

    public Bank checkalance(String accountNumber) {
        return bankRepo.FindByAccountNumber(accountNumber);
    }

    public ClosedAccount closeAccount(String accountNumber) {
        Bank bank = bankRepo.FindByAccountNumber(accountNumber);
        ClosedAccount closedAccount = new ClosedAccount();
        closedAccount.setAccountNumber(bank.getAccountNumber());
        closedAccount.setAddress(bank.getAddress());
        closedAccount.setAmount(bank.getAmount());
        closedAccount.setName(bank.getName());
        closedAccount.setBranchName(bank.getBranchName());
        closedAccount.setIfscCode(bank.getIfscCode());

        closedAccountService.save(closedAccount);
        bankRepo.delete(bank);
        return closedAccount;
    }
}
