package sk.tuke.vmir;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DirectorDao {
    @Query("SELECT * FROM Director")
    List<Director> getAll();

    @Query("SELECT * FROM Director WHERE Id LIKE :Id")
    Director getById(int Id);

    @Insert
    void insertDirectors(Director... directors);

    @Delete
    void deleteDirector(Director director);
}