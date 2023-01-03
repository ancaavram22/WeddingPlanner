package am.solution.weddingplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Vendor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String vendorUser;
    private String vendorName;
    private String paymentStatus;
    private int amount;

    public Vendor(String vendorUser, String vendorName, String paymentStatus, int amount) {
        this.vendorUser = vendorUser;
        this.vendorName = vendorName;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getVendorUser() { return vendorUser; }

    public void setVendorUser(String username) {
        this.vendorUser = username;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getPaymentStatus() {return paymentStatus; }

    public void setPaymentStatus(String status) { this.paymentStatus = status; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }


}
