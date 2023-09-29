package sk.tuke.vmir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


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
        Button qrScan = findViewById(R.id.qr_scan);

        qrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String qrCodeData = result.getContents();
        Activity main = this;

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Action to take");
                builder.setMessage(getMessageFromQrCodeData(qrCodeData));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Action
                        Intent intent = new Intent();
                        if (qrCodeData.startsWith("http://") || qrCodeData.startsWith("https://")) {
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(qrCodeData));
                        } else if (qrCodeData.startsWith("tel:")) {
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(qrCodeData));
                        } else if (qrCodeData.startsWith("mailto:")) {
                            intent.setAction(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse(qrCodeData));
                        } else if (qrCodeData.startsWith("SMSTO:")) {
                            if (ActivityCompat.checkSelfPermission(main, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                intent.setAction(Intent.ACTION_SENDTO);
                                String[] parts = qrCodeData.split(":");
                                String number = parts[1];
                                String message = parts[2];
                                intent.setData(Uri.fromParts("sms", number, message));
                            } else {
                                ActivityCompat.requestPermissions(main, new String[]{Manifest.permission.SEND_SMS}, 2);
                            }
                        } else if (qrCodeData.startsWith("BEGIN:VCARD")) {
                            intent.setAction(Intent.ACTION_INSERT);
                            intent.setData(Uri.parse(qrCodeData));
                        }
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        }
    }

    private String getMessageFromQrCodeData(String qrCodeData) {
        if (qrCodeData.startsWith("http://") || qrCodeData.startsWith("https://")) {
            return "Visit " + qrCodeData + "?";
        } else if (qrCodeData.startsWith("tel:")) {
            return "Call " + qrCodeData + "?";
        } else if (qrCodeData.startsWith("mailto:")) {
            return "Email to " + qrCodeData + "?";
        } else if (qrCodeData.startsWith("SMSTO:")) {
            return "SMS to " + qrCodeData + "?";
        } else if (qrCodeData.startsWith("MECARD:")) {
            return "Add contact  " + qrCodeData + "?";
        }
        return qrCodeData;
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
            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
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