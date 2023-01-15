package am.solution.weddingplanner.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.Vendor;

@Dao
public interface VendorDAO {
    @Query("SELECT * FROM Vendor where  vendorUser= :username ")
    List <Vendor> getAllVendors(String username);

    @Insert
    void insert(Vendor vendor);

    @Update
    void update(Vendor vendor);

    @Delete
    void delete(Vendor vendor);

    @Query("SELECT * FROM Vendor WHERE id = :vendorId")
    Vendor selectDataFromAnId(int vendorId);

    @Query("UPDATE vendor SET vendorUser = :vendorUser,  vendorName = :vendorName, paymentStatus = :paymentStatus, amount = :amount WHERE id = :vendorId")
    void updateAnExistingRow(int vendorId, String vendorUser, String vendorName, String paymentStatus, int amount);

    @Query("SELECT * FROM Vendor where  vendorUser= :username AND paymentStatus=:paid ")
    List <Vendor> getAllPaidVendors(String username, String paid);
}
