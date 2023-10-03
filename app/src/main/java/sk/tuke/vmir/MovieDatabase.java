package sk.tuke.vmir;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import sk.tuke.vmir.Movie;
import sk.tuke.vmir.MovieDao;

@Database(entities = {Movie.class, Director.class}, version = 2,
        autoMigrations = {
                @AutoMigration(from = 1, to = 2)
        },
        exportSchema = true
)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract DirectorDao directorDao();

    public abstract DirectorWithMoviesDao directorWithMoviesDao();
}