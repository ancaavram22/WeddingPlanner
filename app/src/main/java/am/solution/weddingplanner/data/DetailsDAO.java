package am.solution.weddingplanner.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import am.solution.weddingplanner.model.WeddDetails;

@Dao
public interface DetailsDAO {
    @Query("SELECT * FROM WeddDetails where  detailsUser= :username ")
    List <WeddDetails> getAllDetails( String username);

    @Query("SELECT * FROM WeddDetails WHERE id = :detailsId")
    WeddDetails selectDataFromAnId(int detailsId);

    @Query("UPDATE WeddDetails SET  groom = :groom, bride = :bride, date = :date, location = :location, budget = :budget, noOfGuests = :noOfGuests WHERE detailsUser = :username")
    void updateAnExistingRow( String username, String groom, String bride, String date, String location,
                             int budget, int noOfGuests);
    @Query("SELECT * FROM WeddDetails WHERE detailsUser = :username")
    WeddDetails selectDataFromUser(String username);

    @Query("SELECT date FROM WeddDetails WHERE detailsUser = :username")
    String getWeddDate(String username);

    @Query("SELECT budget FROM WeddDetails WHERE detailsUser = :username")
    int getWeddBudget(String username);

    @Query("SELECT noOfGuests FROM WeddDetails WHERE detailsUser = :username")
    int noOfGuestsExpected(String username);

    @Insert
    void insert(WeddDetails details);
}
