package com.arimuntari.simrs.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Checkup implements Parcelable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    private String noRegister;
    private String dateRegister;
    private String time;
    private String priceTotal;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.noRegister);
        dest.writeString(this.dateRegister);
        dest.writeString(this.time);
        dest.writeString(this.priceTotal);
    }

    public Checkup() {
    }

    protected Checkup(Parcel in) {
        this.id = in.readString();
        this.noRegister = in.readString();
        this.dateRegister = in.readString();
        this.time = in.readString();
        this.priceTotal = in.readString();
    }

    public static final Parcelable.Creator<Checkup> CREATOR = new Parcelable.Creator<Checkup>() {
        @Override
        public Checkup createFromParcel(Parcel source) {
            return new Checkup(source);
        }

        @Override
        public Checkup[] newArray(int size) {
            return new Checkup[size];
        }
    };
}
