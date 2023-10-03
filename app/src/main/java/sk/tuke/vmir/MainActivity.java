package sk.tuke.vmir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DirectorAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_director_view);
        layoutManager = new LinearLayoutManager(this);
        DbGetData taskLoadData = new DbGetData();
        taskLoadData.execute();

        context = this;

        Button addButton = findViewById(R.id.add_director_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Director director1 = new Director("added", "click");
                DbAddData taskAddData = new DbAddData();
                taskAddData.execute(director1);
            }
        });
    }

    class DbGetData extends AsyncTask<Void, Integer, List<DirectorWithMovies>> {

        @Override
        protected List<DirectorWithMovies> doInBackground(Void... voids) {
            MovieDatabase db = DbTools.getDbContext(new WeakReference<Context>(MainActivity.this));
            List<Director> data = db.directorDao().getAll();
            Director director1 = new Director("Fist", "Big");
            Director director2 = new Director("Long", "finger");
            if (data.size() == 0) {
                db.directorDao().insertDirectors(director1, director2);
            }
            if (data.size() >= 2) {
                List<Movie> movies1 = db.movieDao().getByDirectorId(data.get(0).getId());
                if (movies1.size() == 0) {
                    Movie movie1 = new Movie("Big", "2004", data.get(0).getId());
                    Movie movie2 = new Movie("Fist", "2005", data.get(0).getId());
                    db.movieDao().insertMovies(movie1, movie2);
                }
                List<Movie> movies2 = db.movieDao().getByDirectorId(data.get(1).getId());
                if (movies2.size() == 0) {
                    Movie movie1 = new Movie("Long", "2004", data.get(1).getId());
                    Movie movie2 = new Movie("Finger", "2005", data.get(1).getId());
                    db.movieDao().insertMovies(movie1, movie2);
                }
            }
            return db.directorWithMoviesDao().getDirectorsWithMovies();
        }

        @Override
        protected void onPostExecute(List<DirectorWithMovies> directors) {
            super.onPostExecute(directors);

            adapter = new DirectorAdapter(directors, new WeakReference<Context>(context));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

    }

    class DbAddData extends AsyncTask<Director, Integer, List<DirectorWithMovies>> {

        @Override
        protected List<DirectorWithMovies> doInBackground(Director... directors) {
            MovieDatabase db = DbTools.getDbContext(new WeakReference<Context>(MainActivity.this));
            db.directorDao().insertDirectors(directors);

            return db.directorWithMoviesDao().getDirectorsWithMovies();
        }

        @Override
        protected void onPostExecute(List<DirectorWithMovies> directors) {
            super.onPostExecute(directors);

            adapter.refreshData(directors);
        }

    }

}