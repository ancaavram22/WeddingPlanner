package am.solution.weddingplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import am.solution.weddingplanner.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false
)

public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDAO getUserDao();

}