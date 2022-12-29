package am.solution.weddingplanner.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import am.solution.weddingplanner.model.Guest;

@Dao
public interface GuestDAO {
    @Query("SELECT * FROM Guest where  guestUser= :username ")
    List <Guest> getAllGuests( String username);

    @Insert
    void insert(Guest guest);

    @Update
    void update(Guest guest);

    @Delete
    void delete(Guest guest);
}
