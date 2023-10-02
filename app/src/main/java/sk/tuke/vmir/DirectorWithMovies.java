package sk.tuke.vmir;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class DirectorWithMovies {
    @Embedded
    public Director director;
    @Relation(
            parentColumn = "Id",
            entityColumn = "director_id"
    )
    public List<Movie> movies;

}
