package com.Bank_System.Repository;

import com.Bank_System.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<Bank,Long> {
//    @Query(value = "select * from bank where account_number=:accountNumber",nativeQuery = true)
     Bank findByAccountNumber(String accountNumber);

     boolean existsByEmailAndAccountNumber(String email, String acNumber);
}
