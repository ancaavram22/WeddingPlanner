package am.solution.weddingplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class WeddDetails implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private String detailsUser;
    @NonNull
    private String groom;
    @NonNull
    private String bride;
    @NonNull
    private String date;
    @NonNull
    private String location;
    @NonNull
    private int budget;
    @NonNull
    private int noOfGuests;



    public WeddDetails(String detailsUser, String groom, String bride, String date, String location, int budget, int noOfGuests) {
        this.detailsUser = detailsUser;
        this.groom = groom;
        this.bride = bride;
        this.date = date;
        this.location = location;
        this.budget = budget;
        this.noOfGuests = noOfGuests;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {this.id = id;}

    public String getDetailsUser() {return detailsUser;}

    public void setDetailsUser(String username) {this.detailsUser = username;}

    public String getGroom() {return groom;}

    public void setGroom(String groom) {this.groom = groom;}

    public String getBride() {return bride;}

    public void setBride(String bride) {this.bride = bride;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public int getBudget() {return budget;}

    public void setBudget(int budget) {this.budget = budget;}

    public int getNoOfGuests() {return noOfGuests;}

    public void setNoOfGuests(int noOfGuests) {this.noOfGuests = noOfGuests;}
}
