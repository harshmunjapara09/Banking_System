package com.Bank_System.Repository;

import com.Bank_System.Model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranscationRepo extends JpaRepository<TransactionHistory, Long> {
    @Query(value = "SELECT * FROM transaction_history WHERE account_number = :accountNumber", nativeQuery = true)
    List<TransactionHistory> getAllTranscation(Long accountNumber);
}
