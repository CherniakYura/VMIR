package sk.tuke.vmir;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo(name = "title")
    private String Title;
    @ColumnInfo(name = "release_year")
    private String Release_year;
    @ColumnInfo(name = "director_id")
    private long DirectorId;

    protected Movie(Parcel in) {
        Id = in.readLong();
        Title = in.readString();
        Release_year = in.readString();
        DirectorId = in.readLong();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRelease_year() {
        return Release_year;
    }

    public void setRelease_year(String release_year) {
        Release_year = release_year;
    }

    public long getDirectorId() {
        return DirectorId;
    }

    public void setDirectorId(long director) {
        DirectorId = director;
    }

    public Movie() {

    }

    public Movie(String title, String released, long director) {
        setTitle(title);
        setRelease_year(released);
        setDirectorId(director);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(Id);
        parcel.writeString(Title);
        parcel.writeString(Release_year);
        parcel.writeLong(DirectorId);
    }
}
