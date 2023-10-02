package sk.tuke.vmir;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DirectorHolder extends RecyclerView.ViewHolder {
    TextView firstName;
    TextView lastName;

    public DirectorHolder(@NonNull View itemView) {
        super(itemView);
        firstName = itemView.findViewById(R.id.tv_firstName);
        lastName = itemView.findViewById(R.id.tv_lastName);
    }
}
