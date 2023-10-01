package com.Bank_System.Service;

import com.Bank_System.Model.TransactionHistory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTransactionHistoryEmail(String recipientEmail, List<TransactionHistory> transactionHistoryList) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Transaction History");

            // Create an HTML table for the transaction history
            StringBuilder transactionHistoryHtml = new StringBuilder();
            transactionHistoryHtml.append("<html><body>");
            transactionHistoryHtml.append("<h2>Transaction History</h2>");
            transactionHistoryHtml.append("<table border='1'><tr><th>Date</th><th>Amount</th><th>Description</th></tr>");

            for (TransactionHistory history : transactionHistoryList) {
                transactionHistoryHtml.append("<tr>");
                transactionHistoryHtml.append("<td>").append(history.getFormattedTime()).append("</td>");
                transactionHistoryHtml.append("<td>").append(history.getAmount()).append("</td>");
                transactionHistoryHtml.append("<td>").append(history.getStatus()).append("</td>");
                transactionHistoryHtml.append("</tr>");
            }

            transactionHistoryHtml.append("</table></body></html>");

            // Set the HTML content as the email body
            helper.setText(transactionHistoryHtml.toString(), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


//    public void sendTransactionHistoryEmail(String recipientEmail, List<TransactionHistory> transactionHistoryList) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(recipientEmail);
//            helper.setSubject("Transaction History");
//            helper.setText("Please find attached the transaction history.");
//
//            // You can format the transaction history as needed and attach it here
//            // For simplicity, we'll assume a plain text format for the example.
//            StringBuilder transactionHistoryText = new StringBuilder();
//            for (TransactionHistory history : transactionHistoryList) {
//                transactionHistoryText.append(history.toString()).append("\n");
//            }
//            helper.setText(transactionHistoryText.toString());
//
//            javaMailSender.send(message);
//        } catch (jakarta.mail.MessagingException e) {
//            e.printStackTrace();
//            // Handle the exception
//        }

    
}

