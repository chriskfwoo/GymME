package chrisjohnny.hardcorders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.DetectionType;
import com.nuance.speechkit.Interpretation;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyLogs extends AppCompatActivity implements View.OnClickListener{

    private Audio startEarcon;
    private Audio stopEarcon;
    private Audio errorEarcon;

    static private TextView logs;
    private Button clearLogs;
    private Button toggleReco;
    private ProgressBar volumeBar;
    private Session speechSession;
    private Transaction recoTransaction;
    private State state = State.IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_logs);


        logs = (TextView)findViewById(R.id.logs);

        clearLogs = (Button)findViewById(R.id.clear_logs);
        clearLogs.setOnClickListener(this);

        toggleReco = (Button)findViewById(R.id.toggle_reco);
        toggleReco.setOnClickListener(this);

        volumeBar = (ProgressBar)findViewById(R.id.volume_bar);

        //Create a session
        speechSession = Session.Factory.session(this, Configuration.SERVER_URI, Configuration.APP_KEY);

        loadEarcons();
        setState(State.IDLE);

    }



    @Override
    public void onClick(View v) {
        if(v == clearLogs) {
            logs.setText("");
        } else if(v == toggleReco) {
            toggleReco();
        }
    }

    private void toggleReco() {
        switch (state) {
            case IDLE:
                recognize();
                break;
            case LISTENING:
                stopRecording();
                break;
            case PROCESSING:
                cancel();
                break;
        }
    }

    private void recognize() {
        //Setup our Reco transaction options.
        Transaction.Options options = new Transaction.Options();
        options.setDetection(DetectionType.Long);
        options.setLanguage(new Language("eng-USA"));
        options.setEarcons(startEarcon, stopEarcon, errorEarcon, null);

        //Add properties to appServerData for use with custom service. Leave empty for use with NLU.
        JSONObject appServerData = new JSONObject();
        //Start listening

        recoTransaction = speechSession.recognizeWithService(Configuration.CONTEXT_TAG, appServerData, options, recoListener);
    }

    private Transaction.Listener recoListener = new Transaction.Listener() {

        public void onStartedRecording(Transaction transaction){
            setState(State.LISTENING);
            toggleReco.setBackgroundResource(R.drawable.voiceinputgreen);
            startAudioLevelPoll();
        }

        public void onFinishedRecording(Transaction transaction){
            setState(State.PROCESSING);
            toggleReco.setBackgroundResource(R.drawable.voiceinput);
            stopAudioLevelPoll();
        }

        public void onServiceResponse(Transaction transaction, org.json.JSONObject response){
            try {
                // 2 spaces for tabulations.
                logs.append("\nonServiceResponse: " + response.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // We have received a service response. In this case it is our NLU result.
            // Note: this will only happen if you are doing NLU (or using a service)
            setState(State.IDLE);

        }

        public void onRecognition(Transaction transaction, Recognition recognition) {
            setState(State.IDLE);
        }

        public void onInterpretation(Transaction transaction, Interpretation interpretation) {

            JSONObject jsonObject1, jsonObject2, jsonObject3, jsonObject4, jsonObject5;
            JSONArray jsonArray1, jsonArray2;

            String weightValue = "";
            String weightUnit = "";
            String exercise = "";
            String repsValue ="";
            String setsValue = "";

            try {

                // WEIGHT VALUE
                jsonObject1 = interpretation.getResult().getJSONArray("interpretations").getJSONObject(0);
                jsonObject2 = jsonObject1.getJSONObject("concepts");
                jsonArray1 = jsonObject2.getJSONArray("WEIGHT");
                jsonObject3 = jsonArray1.getJSONObject(0);
                jsonObject4 = jsonObject3.getJSONObject("concepts");
                jsonArray2 = jsonObject4.getJSONArray("CARDINAL_NUMBER");
                jsonObject5 = jsonArray2.getJSONObject(0);

                weightValue = jsonObject5.getString("value");
                Log.i("Value", weightValue);

                // WEIGHT UNIT
                jsonObject1 = interpretation.getResult().getJSONArray("interpretations").getJSONObject(0);
                jsonObject2 = jsonObject1.getJSONObject("concepts");
                jsonArray1 = jsonObject2.getJSONArray("WEIGHT");
                jsonObject3 = jsonArray1.getJSONObject(0);

                jsonObject4 = jsonObject3.getJSONObject("concepts");
                jsonArray2 = jsonObject4.getJSONArray("WEIGHT_UNIT");
                jsonObject5 = jsonArray2.getJSONObject(0);

                weightUnit = jsonObject5.getString("value");
                Log.i("Value",weightUnit);

                // EXERCISE
                jsonObject1 = interpretation.getResult().getJSONArray("interpretations").getJSONObject(0);
                jsonObject2 = jsonObject1.getJSONObject("concepts");
                jsonArray1 = jsonObject2.getJSONArray("EXERCISE");
                for(int i = 0; i<jsonArray1.length();i++){

                    exercise += jsonArray1.getJSONObject(i).getString("value");
                }
                Log.i("Value",exercise);


                // REPS VALUE
                jsonObject1 = interpretation.getResult().getJSONArray("interpretations").getJSONObject(0);
                jsonObject2 = jsonObject1.getJSONObject("concepts");
                jsonArray1 = jsonObject2.getJSONArray("REPS_VALUE");
                jsonObject3 = jsonArray1.getJSONObject(0);
                jsonObject4 = jsonObject3.getJSONObject("concepts");
                jsonArray2 = jsonObject4.getJSONArray("CARDINAL_NUMBER");
                jsonObject5 = jsonArray2.getJSONObject(0);

                repsValue = jsonObject5.getString("value");
                Log.i("Value",repsValue);

                // SETS VALUE
                jsonObject1 = interpretation.getResult().getJSONArray("interpretations").getJSONObject(0);
                jsonObject2 = jsonObject1.getJSONObject("concepts");
                jsonArray1 = jsonObject2.getJSONArray("SETS_VALUE");
                jsonObject3 = jsonArray1.getJSONObject(0);
                jsonObject4 = jsonObject3.getJSONObject("concepts");
                jsonArray2 = jsonObject4.getJSONArray("CARDINAL_NUMBER");
                jsonObject5 = jsonArray2.getJSONObject(0);

                setsValue = jsonObject5.getString("value");
                Log.i("Value",setsValue);


                //String currentDate = new SimpleDateFormat("dd MM yyyy", Locale.getDefault()).format(new Date());
                Calendar cal = Calendar.getInstance();

                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DATE);
                int year = cal.get(Calendar.YEAR);

                String monthString;
                switch (month) {
                    case 0:  monthString = "January";
                        break;
                    case 1:  monthString = "February";
                        break;
                    case 2:  monthString = "March";
                        break;
                    case 3:  monthString = "April";
                        break;
                    case 4:  monthString = "May";
                        break;
                    case 5:  monthString = "June";
                        break;
                    case 6:  monthString = "July";
                        break;
                    case 7:  monthString = "August";
                        break;
                    case 8:  monthString = "September";
                        break;
                    case 9: monthString = "October";
                        break;
                    case 10: monthString = "November";
                        break;
                    case 11: monthString = "December";
                        break;
                    default: monthString = "Invalid month";
                        break;
                }

                String dateString = monthString + " " +date+", "+year;


                String sentence = dateString+"\n\t\t\t\t\t" +exercise + " - " +weightValue + " " +weightUnit +"\n\t\t\t\t\t"+setsValue+ " sets x " +repsValue+" reps \n\n\n";


                logs.append(sentence);




            } catch (JSONException e) {
                Toast.makeText(MyLogs.this, "Please try again!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            // We have received a service response. In this case it is our NLU result.
            // Note: this will only happen if you are doing NLU (or using a service)

            setState(State.IDLE);

        }

        public void onSuccess(Transaction transaction, String s){


        }

        public void onError(Transaction transaction, String s, TransactionException e){
            setState(State.IDLE);
        }

    };

    private void stopRecording(){
        recoTransaction.stopRecording();
    }

    private void cancel(){
        recoTransaction.cancel();
    }


    private Handler handler = new Handler();

    private Runnable audioPoller = new Runnable() {
        @Override
        public void run() {
            float level = recoTransaction.getAudioLevel();
            volumeBar.setProgress((int)level);
            handler.postDelayed(audioPoller, 50);
        }
    };


    private void startAudioLevelPoll() {
        audioPoller.run();
    }


    private void stopAudioLevelPoll() {
        handler.removeCallbacks(audioPoller);
        volumeBar.setProgress(0);
    }

    private enum State {
        IDLE,
        LISTENING,
        PROCESSING
    }

    private void setState(State newState) {
        state = newState;
        switch (newState) {
            case IDLE:
                //toggleReco.setText(getResources().getString(R.string.recognize_with_service));
                // previewTextView.setText("");
                break;
            case LISTENING:
                // toggleReco.setText(getResources().getString(R.string.listening));
                //previewTextView.setText("listening..");
                break;
            case PROCESSING:
                //toggleReco.setText("Processing..");
                //previewTextView.setText("Processing..");
                break;
        }
    }

    private void loadEarcons() {
        //Load all of the earcons from disk
        startEarcon = new Audio(this, R.raw.sk_start, Configuration.PCM_FORMAT);
        stopEarcon = new Audio(this, R.raw.sk_stop, Configuration.PCM_FORMAT);
        errorEarcon = new Audio(this, R.raw.sk_error, Configuration.PCM_FORMAT);
    }

    /* AUDIO LEVEL POLLING*/



}
