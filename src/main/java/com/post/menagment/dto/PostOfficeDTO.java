package com.post.menagment.dto;

import java.util.Objects;

public class PostOfficeDTO {
    
    private Long id;

    private String adress;

    private boolean isWorking;

    public Long getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public PostOfficeDTO() {
    }
    public PostOfficeDTO(Long id, String adress, boolean isWorking) {
        this.id = id;
        this.adress = adress;
        this.isWorking = isWorking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostOfficeDTO that = (PostOfficeDTO) o;
        return isWorking == that.isWorking && Objects.equals(id, that.id) && Objects.equals(adress, that.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adress, isWorking);
    }

    @Override
    public String toString() {
        return "PostOfficeDTO{" +
                "id=" + id +
                ", adress='" + adress + '\'' +
                ", isWorking=" + isWorking +
                '}';
    }
}
