package chrisjohnny.hardcorders;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Timer extends Activity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    TextView counterTextView;
    Button resetCounter;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    int previousProgress = 60;
    int counter = 1;

    public void voiceInput(View view){
        Log.i("Voice input clicked", "johnny suck a dick");
    }

    public void resetCounter(View view){
        counter = 1;
        counterTextView.setText(Integer.toString(1));
    }

    public void incrementTimer(){
        counter++;
        counterTextView.setText(Integer.toString(counter));
    }

    public void resetTimer(){
        previousProgress /= 15;
        previousProgress *= 15;

        updateTimer(previousProgress);
        timerSeekBar.setProgress(previousProgress);
        countDownTimer.cancel();
        controllerButton.setText("Rest");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft){

        int minutes = secondsLeft / 60;
        int secs = secondsLeft - minutes * 60;

        String secondString = Integer.toString(secs);

        if (secs <=9){
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes)+ ":"+secondString);

    }

    public void controlTimer(View view){

        if (!counterIsActive) {

            previousProgress = timerSeekBar.getProgress();
            previousProgress /= 15;
            previousProgress *= 15;

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(previousProgress * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }
                @Override
                public void onFinish() {

                    timerTextView.setText("0:00");
                    incrementTimer();
                    resetTimer();
                }
            }.start();

        }else {
            incrementTimer();
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        counterTextView = (TextView)findViewById(R.id.counterTextView);
        resetCounter = (Button)findViewById(R.id.resetButton);

        timerSeekBar.setMax(120);
        timerSeekBar.incrementProgressBy(15);
        timerSeekBar.setProgress(60);
        final int minValue = 30;

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < minValue) {
                /* if seek bar value is lesser than min value then set min value to seek bar */
                    progress = 30;
                    seekBar.setProgress(minValue);

                }
                progress = progress / 15;
                progress = progress * 15;
                updateTimer(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
