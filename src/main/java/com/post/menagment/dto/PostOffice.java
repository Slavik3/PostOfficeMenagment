package com.post.menagment.dto;

public class PostOffice {
    
    private long id;

    private String adress;

    private boolean isWorking;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean working) {
        isWorking = working;
    }
}
