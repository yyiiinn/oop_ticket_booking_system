package com.oop.springbootmvc.viewmodel;
import com.oop.springbootmvc.model.Ticket;


public class CustomerTicketViewModel {
    private String id;
    private String type;
    private String status;

    public CustomerTicketViewModel(Ticket t, String type) {
        this.id = t.getGuid();
        this.type = type;
        this.status = t.getStatus();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
