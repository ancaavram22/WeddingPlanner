package am.solution.weddingplanner.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import am.solution.weddingplanner.model.Guest;
import am.solution.weddingplanner.model.Task;

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

    @Query("SELECT * FROM Guest WHERE id = :guestId")
    Guest selectDataFromAnId(int guestId);

    @Query("UPDATE guest SET guestUser = :guestUser,  guestName = :guestName, guestAvailability = :guestAvailability, noOfPers = :noOfPers WHERE id = :guestId")
    void updateAnExistingRow(int guestId, String guestUser, String guestName, String guestAvailability, int noOfPers);
}
