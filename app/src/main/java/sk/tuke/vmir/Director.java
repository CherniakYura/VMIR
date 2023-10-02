package sk.tuke.vmir;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Director {
    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo(name = "first_name")
    private String FirstName;
    @ColumnInfo(name = "last_name")
    private String LastName;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Director() {

    }

    public Director(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
}
