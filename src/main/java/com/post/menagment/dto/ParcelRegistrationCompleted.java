package com.post.menagment.dto;

import java.util.Objects;

public class ParcelRegistrationCompleted {
    private ParcelDTO parcelDTO;
    private Boolean isPostOfficeAvailable;

    public ParcelDTO getParcel() {
        return parcelDTO;
    }

    public Boolean getIsPostOfficeAvailable() {
        return isPostOfficeAvailable;
    }

    public void setParcel(ParcelDTO parcelDTO) {
        this.parcelDTO = parcelDTO;
    }

    public void setAvailable(Boolean isPostOfficeAvailable) {
        isPostOfficeAvailable = isPostOfficeAvailable;
    }

    public ParcelRegistrationCompleted() {

    }

    public ParcelRegistrationCompleted(ParcelDTO parcelDTO, Boolean isPostOfficeAvailable) {
        this.parcelDTO = parcelDTO;
        this.isPostOfficeAvailable = isPostOfficeAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelRegistrationCompleted that = (ParcelRegistrationCompleted) o;
        return Objects.equals(parcelDTO, that.parcelDTO) && Objects.equals(isPostOfficeAvailable, that.isPostOfficeAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parcelDTO, isPostOfficeAvailable);
    }

    @Override
    public String toString() {
        return "SendParcelRegistrationCompleted{" +
                "parcel=" + parcelDTO +
                ", isPostOfficeAvailable=" + isPostOfficeAvailable +
                '}';
    }
}
