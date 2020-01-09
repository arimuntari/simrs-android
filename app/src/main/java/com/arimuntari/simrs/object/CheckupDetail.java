package com.arimuntari.simrs.object;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckupDetail implements Parcelable {
    private String name, price, qty,total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qt) {
        this.qty = qt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.qty);
        dest.writeString(this.total);
    }

    public CheckupDetail() {
    }

    protected CheckupDetail(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.qty = in.readString();
        this.total = in.readString();
    }

    public static final Parcelable.Creator<CheckupDetail> CREATOR = new Parcelable.Creator<CheckupDetail>() {
        @Override
        public CheckupDetail createFromParcel(Parcel source) {
            return new CheckupDetail(source);
        }

        @Override
        public CheckupDetail[] newArray(int size) {
            return new CheckupDetail[size];
        }
    };
}
