package com.oop.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.springbootmvc.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public int revenueGeneratedByEventID(String status, int eventId) {
        return transactionRepository.revenueGeneratedByEventID(status, eventId);
    }
    
}
