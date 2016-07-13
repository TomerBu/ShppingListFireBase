package tomerbu.edu.shppinglistfirebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tomerbuzaglo on 30/06/2016.
 * Copyright 2016 tomerbuzaglo. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0
 * you may not use this file except
 * in compliance with the License
 */
public class ShoppingList extends BaseModel implements Parcelable {
    private String owner;
    private String listName;
    private String UID;
    //Default constructor
    public ShoppingList() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public ShoppingList(String owner, String listName, String UID) {
        this.owner = owner;
        this.listName = listName;
        this.UID = UID;

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.owner);
        dest.writeString(this.listName);
        dest.writeString(this.UID);
    }

    protected ShoppingList(Parcel in) {
        this.owner = in.readString();
        this.listName = in.readString();
        this.UID = in.readString();
    }

    public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel source) {
            return new ShoppingList(source);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[size];
        }
    };
}
