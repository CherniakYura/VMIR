package sk.tuke.vmir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;


public class MovieListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<MovieHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        recyclerView = findViewById(R.id.rv_movie_list);
        layoutManager = new LinearLayoutManager(this);
        Intent intent = getIntent();
        List<Movie> movies = intent.getParcelableArrayListExtra("movies");
        Long directorId = intent.getLongExtra("directorId", 0);
        System.out.println(" DSDS    j" + movies.size());
        System.out.println("directorId " + directorId);
        adapter = new MovieAdapter(movies, new WeakReference<Context>(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}