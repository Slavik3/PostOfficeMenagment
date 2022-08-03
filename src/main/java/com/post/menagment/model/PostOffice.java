package com.post.menagment.model;

import javax.persistence.*;
import java.util.Objects;

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

    public PostOffice() {
    }

    public PostOffice(long id, String adress, boolean isWorking) {
        this.id = id;
        this.adress = adress;
        this.isWorking = isWorking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostOffice that = (PostOffice) o;
        return id == that.id && isWorking == that.isWorking && adress.equals(that.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adress, isWorking);
    }

    @Override
    public String toString() {
        return "PostOffice{" +
                "id=" + id +
                ", adress='" + adress + '\'' +
                ", isWorking=" + isWorking +
                '}';
    }
}
