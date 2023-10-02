package sk.tuke.vmir;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView releaseYear;
//    TextView directorName;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_title);
        releaseYear = itemView.findViewById(R.id.tv_released);
//        directorName = itemView.findViewById(R.id.tv_director);
    }
}
