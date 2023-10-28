package com.Bank_System.Service;

import com.Bank_System.Model.User;
import com.Bank_System.Model.utility.OTPGenerator;
import com.Bank_System.Repository.BankRepo;
import com.Bank_System.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    BankRepo bankRepo;

    @Autowired
    EmailService emailService;
    public String loginUser(String email)  {
        User user = userRepo.getByUserEmail(email);
        String otp = OTPGenerator.generateOTP();
        user.setOtp(otp);
        userRepo.save(user);
        emailService.sendOtpEmail(email, otp);

        return "Otp Sent Successfully";
    }

    public String createUser(String email, String acNumber) {
        if (email==null || acNumber==null){
            return "Invalid Email and Account Number";
        }

        boolean recordExists = bankRepo.existsByEmailAndAccountNumber(email, acNumber);

        if (recordExists){
            boolean recordExistsUser = userRepo.existsByUserEmailAndUserAccountNumber(email, acNumber);
            if (recordExistsUser){
                return "Already Exists";
            }else {
                User user = new User();
                user.setUserEmail(email);
                user.setUserAccountNumber(acNumber);
                user.setOperation("Open");
                userRepo.save(user);
                return "Register Successfully Created";
            }
        }

        return "Invalid Details";
    }

    public String verifyOtp(String otp) {
        User user = userRepo.getByOtp(otp);
        if (user==null){
            return "Invalid Otp";
        }
        user.setStatus("Logged");
        userRepo.save(user);
        return "Logged In";
    }

    public String signOut(String email) {
        User user = userRepo.getByUserEmail(email);
        user.setStatus("logout");
        userRepo.save(user);
        return "Logout Successfully";
    }
}
