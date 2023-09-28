package sk.tuke.vmir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    public boolean isTextOn = false;
    private TextView scoreTextView;
    private TextView timeTextView;
    private DrawView drawView;

    private int score;
    private long finish;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            updateTime();
            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Starting App...");
//
//        scoreTextView = findViewById(R.id.score_text_view);
//        timeTextView = findViewById(R.id.time_text_view);
//        drawView = findViewById(R.id.draw_view);
//        drawView.callback = new UpdateCountCallback() {
//            @Override
//            public void update() {
//                updateScore();
//            }
//        };
//
//        score = 0;
//        finish = System.currentTimeMillis() + 1000 * 10;
//
//        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void updateScore() {
        score++;
        scoreTextView.setText("Score: " + String.valueOf(score));
    }

    public void updateTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = finish - currentTime;
        if (elapsedTime >= 0) {
            timeTextView.setText("Time left: 00:" + String.format("%02d", elapsedTime / 1000));
            return;
        }
        drawView.finished = true;
    }

//    public void pressAction(View view) {
//        TextView label = (TextView) findViewById(R.id.label);
//        label.setText(isTextOn ? "Lorem ipsum" : "Not lorem ipsum");
//        isTextOn = !isTextOn;
//
//        Intent intent = new Intent(this, SecondActivity.class);
//        intent.putExtra("lab", 69);
//        intent.putExtra("bool", true);
//        intent.putExtra("string", "string");
//        intent.putExtra("float", 4.20F);
//        startActivity(intent);
//    }
}