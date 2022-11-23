package am.solution.weddingplanner.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import am.solution.weddingplanner.model.Task;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM Task where  taskUser= :username ")
    Task getTask( String username);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
