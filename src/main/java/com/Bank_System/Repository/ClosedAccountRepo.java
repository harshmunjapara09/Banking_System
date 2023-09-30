package com.Bank_System.Repository;

import com.Bank_System.Model.ClosedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedAccountRepo extends JpaRepository<ClosedAccount,Long> {
}
