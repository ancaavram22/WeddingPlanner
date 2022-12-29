package am.solution.weddingplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Guest implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String guestUser;
    private String guestName;
    private String guestAvailability;
    private String noOfPers;


    public Guest(String guestUser, String guestName, String guestAvailability, String noOfPers) {
        this.guestUser = guestUser;
        this.guestName = guestName;
        this.guestAvailability = guestAvailability;
        this.noOfPers = noOfPers;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getGuestUser() { return guestUser; }

    public void setTaskUser(String username) {
        this.guestUser = username;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String title) { this.guestAvailability = title; }

    public String getGuestAvailability() {return guestAvailability; }

    public void setGuestAvailability(String description) { this.guestAvailability = description; }

    public String getNoOfPers() { return noOfPers; }

    public void setNoOfPers(String date) { this.noOfPers = date; }


}
