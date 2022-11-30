package am.solution.weddingplanner.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import am.solution.weddingplanner.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false
)

public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDAO getTaskDao();

}