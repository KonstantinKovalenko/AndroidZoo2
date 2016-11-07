package com.example.admin.zoo2.database;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int userId = -1;

    private String userName;
    private String userPassword;
    private boolean isAdmin;

    public User(String userName) {
        this.setUserName(userName);
        setUserPassword("");
        setAdmin(false);
    }

    public User(String userName, String userPassword) {
        this.setUserName(userName);
        this.setUserPassword(userPassword);
        setAdmin(false);
    }

    public User(String userName, String userPassword, boolean isAdmin) {
        this.setUserName(userName);
        this.setUserPassword(userPassword);
        this.setAdmin(isAdmin);
    }

    public User(int userId, String userName, String userPassword, boolean isAdmin) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setUserPassword(userPassword);
        this.setAdmin(isAdmin);
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    protected User(Parcel in) {
        setUserId(in.readInt());
        setUserName(in.readString());
        setUserPassword(in.readString());
        setAdmin(in.readByte() != 0);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getUserId());
        parcel.writeString(getUserName());
        parcel.writeString(getUserPassword());
        parcel.writeByte((byte) (isAdmin() ? 1 : 0));
    }
}
