package com.Bank_System.Controller;

import com.Bank_System.Model.User;
import com.Bank_System.Service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String createUser(@RequestParam String email, String acNumber){
        return userService.createUser(email,acNumber);
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam String email)  {
        return userService.loginUser(email);
    }

    @GetMapping("/verify")
    public String verifyOtp(@RequestParam String otp){
        return userService.verifyOtp(otp);
    }

    @PostMapping("/logout")
    public String signOut(@RequestParam String email){
        return userService.signOut(email);
    }


}
