package com.oop.springbootmvc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.repository.EventRepository;

@Component
public class EventStatusUpdater {

    private final EventRepository eventRepository;

    public EventStatusUpdater(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void updateEventStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTimeNow = LocalTime.now(ZoneId.systemDefault());
        LocalTime currentTime = LocalTime.of(currentTimeNow.getHour(), currentTimeNow.getMinute(), currentTimeNow.getSecond());
        LocalDateTime currentDateTime = LocalDateTime.now();

        // find events that are within sales period and update status to Active
        List<Event> eventsToUpdate = eventRepository.findBySalesStartDateTimeBeforeAndStatusNot(currentDateTime);
        for (Event event : eventsToUpdate) {
            event.setStatus("Active");
            eventRepository.save(event);
        }

        // find events where sales period are over and update to Upcoming
        List<Event> eventsToUpdateToUpcoming = eventRepository.findAfterSalesPeriodEvent(currentDateTime);
        for (Event event : eventsToUpdateToUpcoming) {
            event.setStatus("Upcoming");
            eventRepository.save(event);
        }

        // find events where event is ongoing and update to Ongoing
        List<Event> eventsToUpdateToOngoing = eventRepository.findDuringEventPeriod(currentDate, currentTime);
        System.out.println(eventsToUpdateToOngoing);
        for (Event event : eventsToUpdateToOngoing) {
            event.setStatus("Ongoing");
            eventRepository.save(event);
        }

        // find events where event has ended
        List<Event> eventsToUpdateToEnded = eventRepository.findEventEnded(currentDate, currentTime);
        for (Event event : eventsToUpdateToEnded) {
            event.setStatus("Ended");
            eventRepository.save(event);
        }
    }
}
