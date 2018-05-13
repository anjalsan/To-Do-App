package todolist.task.com.todolist.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import todolist.task.com.todolist.model.Todo;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Todo todo);

    @Update
    void update(Todo todo);
}
