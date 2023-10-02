package sk.tuke.vmir;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE Id LIKE :Id")
    Movie getById(int Id);

    @Query("SELECT * FROM Movie WHERE director_id LIKE :Id")
    List<Movie> getByDirectorId(long Id);

    @Insert
    void insertMovies(Movie... movies);

    @Delete
    void deleteMovie(Movie movie);
}