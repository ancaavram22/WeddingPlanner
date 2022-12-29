package am.solution.weddingplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import am.solution.weddingplanner.model.Guest;

@Database(entities = {Guest.class}, version = 1, exportSchema = false
)

public abstract class GuestDataBase extends RoomDatabase {

    public abstract GuestDAO getGuestDao();

}