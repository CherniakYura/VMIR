package sk.tuke.vmir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    public boolean isTextOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Starting App...");

        Toast.makeText(this, "OnCreate is called.", Toast.LENGTH_SHORT).show();
    }

    public void pressAction(View view) {
        TextView label = (TextView) findViewById(R.id.label);
        label.setText(isTextOn ? "Lorem ipsum" : "Not lorem ipsum");
        isTextOn = !isTextOn;

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("lab", 69);
        startActivity(intent);
    }
}