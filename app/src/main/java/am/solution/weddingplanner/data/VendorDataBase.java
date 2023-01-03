package am.solution.weddingplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.Vendor;

@Database(entities = {Vendor.class}, version = 1, exportSchema = false
)

public abstract class VendorDataBase extends RoomDatabase {

    public abstract VendorDAO getVendorDao();

}