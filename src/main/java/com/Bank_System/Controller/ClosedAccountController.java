package com.Bank_System.Controller;

import com.Bank_System.Model.ClosedAccount;
import com.Bank_System.Service.ClosedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("HarshBank")
public class ClosedAccountController {

    @Autowired
    ClosedAccountService closedAccountService;

    @PostMapping("/save")
    public ClosedAccount save(ClosedAccount bank){
        return closedAccountService.save(bank);
    }
}
