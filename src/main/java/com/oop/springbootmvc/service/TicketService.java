package com.oop.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.springbootmvc.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public int countTicketsByEventId(int eventId) {
        return ticketRepository.countByEventId(eventId);
    }

    public int customerAttendanceByEventID(String status, int eventId) {
        return ticketRepository.customerAttendanceByEventID(status, eventId);
    }

    public int totalTicketsSold() {
        return ticketRepository.totalTicketsSold();
    }

    public int totalCustomerAttendance(String status) {
        return ticketRepository.totalCustomerAttendance(status);
    }
}
