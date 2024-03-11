package com.elevenqtwo.Effective_Mobile_TestApp.controller;

import com.elevenqtwo.Effective_Mobile_TestApp.dto.TransferRequestDto;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.InsufficientBalanceException;
import com.elevenqtwo.Effective_Mobile_TestApp.exception.UserNotFoundException;
import com.elevenqtwo.Effective_Mobile_TestApp.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
public class UserTransactionController {
    private final TransactionService transactionService;

    public UserTransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransferRequestDto request) {
        try {
            transactionService.transfer(
                    request.getSourceUserId(),
                    request.getDestinationUserId(),
                    request.getAmount());
        } catch (UserNotFoundException | InsufficientBalanceException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Transfer successful");
    }
}
