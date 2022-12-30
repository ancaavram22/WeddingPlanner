package am.solution.weddingplanner.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
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
}
