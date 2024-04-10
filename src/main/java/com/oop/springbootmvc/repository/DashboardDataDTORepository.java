package com.oop.springbootmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oop.springbootmvc.model.DashboardDataDTO;

@Repository
public interface DashboardDataDTORepository extends CrudRepository<DashboardDataDTO, Long>{
    
    @Query("SELECT new com.oop.springbootmvc.model.DashboardDataDTO(ti.id, ti.guid, ti.status, t.id, t.cancellationCost, t.status, t.purchasedDateTime, t.cost, s.id, s.type, s.cost, s.numberOfSeats, e.id, e.name, e.description, e.category, e.venue, e.cancellationFee, e.eventStartDate, e.eventStartTime, e.eventEndTime, e.ticketSaleStartDateTime, e.ticketSaleEndDateTime,e.status) " +
        "FROM Ticket ti JOIN ti.transaction t JOIN t.seat s Join s.event e")
    List<DashboardDataDTO> getDashboardData();
}
