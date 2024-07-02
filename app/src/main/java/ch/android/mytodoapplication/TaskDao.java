package ch.android.mytodoapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table")
    List<Task> selectAll();

    @Insert
    void insert(Task item);

    @Update
    void update(Task item);

    @Delete
    void  delete(Task item);

    @Query("SELECT * FROM task_table WHERE id = :id")
    Task selectById(int id);
}
