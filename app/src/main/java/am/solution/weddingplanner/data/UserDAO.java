package am.solution.weddingplanner.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import am.solution.weddingplanner.model.User;
@Dao
public interface UserDAO {
    @Query("SELECT * FROM User where email= :email and password= :password")
    User getUser( String email, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
