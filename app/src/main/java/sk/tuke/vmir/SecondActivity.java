package sk.tuke.vmir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        int labNum = intent.getIntExtra("lab", 0);
        boolean bool = intent.getBooleanExtra("bool", false);
        String string = intent.getStringExtra("string");
        Float labFloat = intent.getFloatExtra("float", 0.9F);

        Toast.makeText(this, "Second one is created: " + labNum + " " + bool + " " + string + " " + labFloat, Toast.LENGTH_SHORT).show();
    }
}