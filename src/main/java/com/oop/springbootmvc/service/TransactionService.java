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

    public int countTotalTransactions() {
        return transactionRepository.countTotalTransactions();
    }
    
    public int totalRevenueGenerated(String status) {
        return transactionRepository.totalRevenueGenerated(status);
    }

    public int countTransactionByStatus(String status) {
        return transactionRepository.countTransactionByStatus(status);
    }

    public int countTransactionByStatusAndSeatId(String status, int seat_id) {
        return transactionRepository.countTransactionByStatusAndSeatId(status, seat_id);
    }
}
