package com.oop.springbootmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;
import com.oop.springbootmvc.viewmodel.SeatViewModel;
import com.oop.springbootmvc.repository.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // public List<Seat> getSeatsByEventId(int event_id) {
    //     try {
    //         return seatRepository.findSeatByEventId(event_id);
    //     } catch (Exception e){
    //         e.printStackTrace();
    //         return new ArrayList<>();
    //     }
    // }

    public Boolean createSeatsByEvent(Event event, List<SeatViewModel> seatViewModels) {  
      try {
          if (deleteSitByEvent(event)) {
            for (SeatViewModel seatViewModel : seatViewModels) {
                Seat seat = new Seat(seatViewModel.getType(), seatViewModel.getCost(), seatViewModel.getNumberOfSeats(), event);
                seatRepository.save(seat);
              }
              return true;
          }
          return false;
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
    }

    public boolean deleteSitByEvent(Event event) {
        try {
            if (event.getSeats() != null){
                for (Seat s: event.getSeats()){
                    seatRepository.delete(s); 
                }
            }
            // int rowsAffected = sitRepository.deleteSitByEventId(eventId);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false; 
        }
    }
    
}
