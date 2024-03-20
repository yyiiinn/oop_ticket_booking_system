package com.oop.springbootmvc.viewmodel;

public class GuidViewModel {
    private String guid;

    public GuidViewModel(String guid) {
        this.guid = guid;
     
    }

    public GuidViewModel() {

    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

}
