package tomerbu.edu.shppinglistfirebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines the data structure for User objects.
 */
public class User extends BaseModel implements Parcelable {
    private String name;
    private String email;
    private String UID;


    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private boolean isLoggedIn;


    /**
     * Required public constructor
     */
    public User() {
    }


    public User(String email, String UID, boolean isLoggedIn) {
        this.name = email.split("@")[0];
        this.email = email;
        this.isLoggedIn = isLoggedIn;
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.UID);
        dest.writeByte(this.isLoggedIn ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.UID = in.readString();
        this.isLoggedIn = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}