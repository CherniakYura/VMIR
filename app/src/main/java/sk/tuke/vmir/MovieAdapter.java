package sk.tuke.vmir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private List<Movie> _data;
    private WeakReference<Context> _context;

    public MovieAdapter(List<Movie> data, WeakReference<Context> contextReference) {
        _context = contextReference;
        _data = data;
    }

    public void refreshData(List<Movie> data) {
        _data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.releaseYear.setText(_data.get(i).getRelease_year());
        movieHolder.title.setText(_data.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        if (_data != null)
            return _data.size();
        return 0;
    }
}