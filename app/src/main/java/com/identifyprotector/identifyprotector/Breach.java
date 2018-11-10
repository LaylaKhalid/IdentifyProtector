package com.identifyprotector.identifyprotector;

import android.os.Parcel;
import android.os.Parcelable;

public class Breach implements Parcelable {
    private String name;
    private String title;
    private String description;
    private String account;

    public Breach(String name, String title, String description, String account) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.account = account;
    }

    public static final Parcelable.Creator<Breach> CREATOR = new Creator<Breach>() {
        @Override
        public Breach createFromParcel(Parcel in) {
            return new Breach(in);
        }

        @Override
        public Breach[] newArray(int size) {
            return new Breach[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(title);
        out.writeString(description);
        out.writeString(account);
    }
    private Breach(Parcel in) {
        this.name = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.account = in.readString();
    }
}
