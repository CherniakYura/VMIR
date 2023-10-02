package sk.tuke.vmir;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DirectorWithMoviesDao {
    @Transaction
    @Query("SELECT * FROM Director")
    public List<DirectorWithMovies> getDirectorsWithMovies();

}