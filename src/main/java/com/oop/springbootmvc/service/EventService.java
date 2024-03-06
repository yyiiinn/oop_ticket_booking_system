package com.oop.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oop.springbootmvc.repository.EventRepository;
import com.oop.springbootmvc.model.Event;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findActiveEvents() {
        return eventRepository.findActiveEvents();
    }
}
