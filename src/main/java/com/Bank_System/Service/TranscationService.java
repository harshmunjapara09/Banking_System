package com.Bank_System.Service;

import com.Bank_System.Model.TransactionHistory;
import com.Bank_System.Repository.TranscationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TranscationService {
    
    @Autowired
    TranscationRepo transcationRepo;


    public TranscationService(TranscationRepo transcationRepo) {
        this.transcationRepo = transcationRepo;
    }

    public TransactionHistory add(TransactionHistory transactionHistory) {
        return transcationRepo.save(transactionHistory);
    }

    public List<TransactionHistory> getAll(Long accountNumber) {
        return transcationRepo.getAllTranscation(accountNumber);
    }
}
