package sk.tuke.vmir;

import android.content.Context;

import androidx.room.Room;

import java.lang.ref.WeakReference;

public class DbTools {
    private static MovieDatabase _db;

    public DbTools(WeakReference<Context> refContext) {
        _db = getDbContext(refContext);
    }

    public static MovieDatabase getDbContext(WeakReference<Context> refContext) {
        if (_db != null)
            return _db;
        return Room.databaseBuilder(refContext.get(), MovieDatabase.class, "movie-db").build();
    }
}
