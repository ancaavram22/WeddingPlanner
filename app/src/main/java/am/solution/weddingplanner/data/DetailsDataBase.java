package am.solution.weddingplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import am.solution.weddingplanner.model.Task;
import am.solution.weddingplanner.model.WeddDetails;

@Database(entities = {WeddDetails.class}, version = 1, exportSchema = false
)

public abstract class DetailsDataBase extends RoomDatabase {

    public abstract DetailsDAO getDetailsDao();

}