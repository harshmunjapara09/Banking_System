package com.Bank_System.Service;

import com.Bank_System.Model.Bank;
import com.Bank_System.Model.ClosedAccount;
import com.Bank_System.Model.TransactionHistory;
import com.Bank_System.Model.User;
import com.Bank_System.Repository.BankRepo;
import com.Bank_System.Repository.TranscationRepo;
import com.Bank_System.Repository.UserRepo;
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
    @Autowired
    TranscationRepo transcationRepo;
    @Autowired
    UserRepo userRepo;


    public List<Bank> openingAccount(List<Bank> list) {
        return bankRepo.saveAll(list);
    }

    public String findByAccountNumberforDeposite(String accountNumber, Double amount) {
        User user = userRepo.getByUserAccountNumber(accountNumber);
        Bank update = bankRepo.findByAccountNumber(accountNumber);

        if (user==null){
            return "Please Create a Net Banking account";
        }
        if (!user.getStatus().equals("Logged")){
            return "Please Login First";
        }
        Double currentAmount = update.getAmount();
        currentAmount += amount;
        update.setAccountNumber(accountNumber);
        update.setAmount(currentAmount);

        TransactionHistory th = new TransactionHistory();
        th.setBranchName(update.getBranchName());
        th.setIfscCode(update.getIfscCode());
        th.setName(update.getName());
        th.setAmount(amount);
        Long transactionId = Math.abs(generateUniqueTransactionId());
        th.setTransactionId(transactionId);
        th.setAccountNumber(update.getAccountNumber());
        th.setStatus("Deposite");
        transcationService.add(th);

        bankRepo.save(update);
        return "Deposit Successfully : " + amount;
    }

    public String findByAccountNumberforWithdraw(String accountNumber, Double amount) {
        User user = userRepo.getByUserAccountNumber(accountNumber);
        Bank update = bankRepo.findByAccountNumber(accountNumber);
        if (user==null){
            return "Please Create a Net Banking account";
        }

        if (!user.getStatus().equals("Logged")){
            return "Please Login First";
        }
        if (update.getAmount() < amount) {
            return "Not a Balance";
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
        Long transactionId = Math.abs(generateUniqueTransactionId());
        th.setTransactionId(transactionId);
        th.setAccountNumber(update.getAccountNumber());
        th.setStatus("Withdraw");
        transcationService.add(th);

        bankRepo.save(update);
        return "Withdraw Successfully : " + amount;
    }

    private Long generateUniqueTransactionId() {
        long timestamp = new Date().getTime();

        Random random = new Random();
        long randomNumber = random.nextLong();

        return timestamp + randomNumber;
    }


    public String transferAmount(String senderAccount, String receiverAccount, Double amount) {

        User user = userRepo.getByUserAccountNumber(senderAccount);
        if (user==null){
            return "Please Create a Net Banking account";
        }
        if (!user.getStatus().equals("Logged")){
            return "Please Login First";
        }
        Bank sender = bankRepo.findByAccountNumber(senderAccount);
        Bank recevicer = bankRepo.findByAccountNumber(receiverAccount);

        Double currentAmountSender = sender.getAmount();
        if (currentAmountSender < amount) {
            return "Not a Balance";
        }
        currentAmountSender -= amount;
        sender.setAccountNumber(sender.getAccountNumber());
        sender.setAmount(currentAmountSender);

        TransactionHistory th = new TransactionHistory();
        th.setBranchName(sender.getBranchName());
        th.setIfscCode(sender.getIfscCode());
        th.setName(sender.getName());
        th.setAmount(amount);
        Long transactionId = Math.abs(generateUniqueTransactionId());
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
        return "Sender Accounr  : " +  senderAccount + "  Receiver Account : " + receiverAccount + " Amount : " + amount;
    }

    public String checkalance(String accountNumber) {
        User user = userRepo.getByUserAccountNumber(accountNumber);
        if (user==null){
            return "Please Create a Net Banking account";
        }
        if (!user.getStatus().equals("Logged")){
            return "Please Login First";
        }
        Bank bank = bankRepo.findByAccountNumber(accountNumber);
        return "Your Balance :  " + bank.getAmount();
    }

    public ClosedAccount closeAccount(String accountNumber) {
        Bank bank = bankRepo.findByAccountNumber(accountNumber);
        List<TransactionHistory> list = new ArrayList<>();
        list = transcationService.getAll(Long.parseLong(accountNumber));
        ClosedAccount closedAccount = new ClosedAccount();
        closedAccount.setAccountNumber(bank.getAccountNumber());
        closedAccount.setAddress(bank.getAddress());
        closedAccount.setAmount(bank.getAmount());
        closedAccount.setName(bank.getName());
        closedAccount.setBranchName(bank.getBranchName());
        closedAccount.setIfscCode(bank.getIfscCode());

        closedAccountService.save(closedAccount);
        bankRepo.delete(bank);
        transcationRepo.deleteAll(list);

        return closedAccount;
    }
}
