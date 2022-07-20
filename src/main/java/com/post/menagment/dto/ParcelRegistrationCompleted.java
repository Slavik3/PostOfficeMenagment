package com.post.menagment.dto;

public class ParcelRegistrationCompleted {
    private Parcel parcel;
    private Boolean isPostOfficeAvailable;

    public Parcel getParcel() {
        return parcel;
    }

    public Boolean getIsPostOfficeAvailable() {
        return isPostOfficeAvailable;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public void setAvailable(Boolean isPostOfficeAvailable) {
        isPostOfficeAvailable = isPostOfficeAvailable;
    }

    public ParcelRegistrationCompleted(Parcel parcel, Boolean isPostOfficeAvailable) {
        this.parcel = parcel;
        this.isPostOfficeAvailable = isPostOfficeAvailable;
    }

    @Override
    public String toString() {
        return "SendParcelRegistrationCompleted{" +
                "parcel=" + parcel +
                ", isPostOfficeAvailable=" + isPostOfficeAvailable +
                '}';
    }
}
