package com.Bank_System.Repository;

import com.Bank_System.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User getByUserEmail(String email);

    boolean existsByUserEmailAndUserAccountNumber(String email, String acNumber);

    User getByOtp(String otp);

    User getByUserAccountNumber(String accountNumber);
}
