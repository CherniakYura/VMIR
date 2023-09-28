package sk.tuke.vmir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    private Button callBtn;
    private Button backspace;
    private String numberStr = "";
    private TextView numberView;

    public void setNumberStr(String s) {
        numberStr = s;
        updateNum();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Starting App...");

        callBtn = (Button) findViewById(R.id.callBtn);
        numberView = (TextView) findViewById(R.id.textView);

        Activity main = this;
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(main, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    ActivityCompat.requestPermissions(main, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        });

        backspace = (Button) findViewById(R.id.backspace);
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberStr != null && numberStr.length() > 0) {
                    setNumberStr(numberStr.substring(0, numberStr.length() - 1));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + numberStr));
        startActivity(callIntent);
    }

    public void addNum(View v) {
        String buttonText = ((Button) v).getText().toString();
        setNumberStr(numberStr + buttonText);
    }


    public void updateNum() {
        numberView.setText(numberStr);
    }

}