package sk.tuke.vmir;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DirectorAdapter extends RecyclerView.Adapter<DirectorHolder> {
    private List<DirectorWithMovies> _data;
    private WeakReference<Context> _context;

    public DirectorAdapter(List<DirectorWithMovies> data, WeakReference<Context> contextReference) {
        _context = contextReference;
        _data = data;
    }

    public void refreshData(List<DirectorWithMovies> data) {
        _data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DirectorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.director_item, viewGroup, false);
        return new DirectorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorHolder directorHolder, int i) {
        Director director = _data.get(i).director;
        directorHolder.firstName.setText(director.getFirstName());
        directorHolder.lastName.setText(director.getLastName());

        directorHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long directorId = director.getId();

                ArrayList<Movie> movies = new ArrayList<>(DbTools.getDbContext(_context).movieDao().getByDirectorId(directorId));

                Intent intent = new Intent(view.getContext(), MovieListActivity.class);
                intent.putParcelableArrayListExtra("movies", movies);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (_data != null)
            return _data.size();
        return 0;
    }
}