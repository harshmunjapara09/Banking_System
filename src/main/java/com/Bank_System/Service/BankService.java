package com.Bank_System.Service;

import com.Bank_System.Model.Bank;
import com.Bank_System.Repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepo bankRepo;

    public List<Bank> openingAccount(List<Bank> list) {
        return bankRepo.saveAll(list);
    }

    public Bank FindByAccountNumberforDeposite(String accountNumber, Double amount) {
        Bank update = bankRepo.FindByAccountNumber(accountNumber);
        Double currentAmount = update.getAmount();
        currentAmount += amount;
        update.setAccountNumber(accountNumber);
        update.setAmount(currentAmount);
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
        return bankRepo.save(update);
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

        Double currentAmountRecevier = recevicer.getAmount();
        currentAmountRecevier += amount;
        recevicer.setAccountNumber(recevicer.getAccountNumber());
        recevicer.setAmount(currentAmountRecevier);

        bankRepo.save(recevicer);
        bankRepo.save(sender);
        return list;
    }
}
