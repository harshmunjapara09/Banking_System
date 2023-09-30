package com.Bank_System.Service;

import com.Bank_System.Model.ClosedAccount;
import com.Bank_System.Repository.ClosedAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClosedAccountService {
    @Autowired
    ClosedAccountRepo closedAccountRepo;

    public ClosedAccount save(ClosedAccount bank){
        return closedAccountRepo.save(bank);
    }

}
