package com.oop.springbootmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oop.springbootmvc.model.Sit;
import com.oop.springbootmvc.viewmodel.SitViewModel;
import com.oop.springbootmvc.repository.SitRepository;

@Service
public class SitService {

    private final SitRepository sitRepository;

    public SitService(SitRepository sitRepository) {
        this.sitRepository = sitRepository;
    }

    public List<Sit> getSitsByEventId(int event_id) {
        try {
            return sitRepository.findSitByEventId(event_id);
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Boolean createSitsByEventId(int event_id, List<SitViewModel> sitViewModels) {  
      try {
          boolean deletedSits = deleteSitByEventId(event_id);
          if (deletedSits == true) {
            for (SitViewModel sitViewModel : sitViewModels) {
                Sit sit = new Sit(event_id, sitViewModel.getType(), sitViewModel.getCost(), sitViewModel.getNumberOfSeats());
                Sit createdSit = sitRepository.save(sit);
              }
              return true;
          }
          return false;
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
    }

    public boolean deleteSitByEventId(int eventId) {
        try {
            int rowsAffected = sitRepository.deleteSitByEventId(eventId);
            return rowsAffected >= 0;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false; 
        }
    }
    
}
