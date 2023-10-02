package sk.tuke.vmir;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    public static List<MovieModel> getMovieData() {
        List<MovieModel> outputModel = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            outputModel.add(new MovieModel(
                    "Title " + Integer.toString(i),
                    "Released " + Integer.toString(i),
                    "Director " + Integer.toString(i)
            ));
        }
        return outputModel;
    }

    public static List<DirectorModel> getDirectorData() {
        List<DirectorModel> outputModel = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            outputModel.add(new DirectorModel(
                    "First name " + Integer.toString(i),
                    "Last name " + Integer.toString(i)
            ));
        }
        return outputModel;
    }
}
