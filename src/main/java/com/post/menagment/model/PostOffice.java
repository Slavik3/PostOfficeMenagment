package com.post.menagment.model;

import javax.persistence.*;

@Entity
@Table(name = "post_office")
public class PostOffice {
    @Id
    @GeneratedValue
    private long id;

    private String adress;

    @Column(name = "is_working")
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
