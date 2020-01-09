package com.arimuntari.simrs.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
    private String id;
    private String code;
    private String name;
    private String birthdate;
    private String phone_number;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Patient() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.birthdate);
        dest.writeString(this.phone_number);
        dest.writeString(this.address);
    }

    protected Patient(Parcel in) {
        this.id = in.readString();
        this.code = in.readString();
        this.name = in.readString();
        this.birthdate = in.readString();
        this.phone_number = in.readString();
        this.address = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
