package chrisjohnny.hardcorders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        ParseApplication parseApplication = new ParseApplication();
//        parseApplication.onCreate();
    }

    public void tappedMyInfo(View vew){
        Log.i("You tapped:", "My Info");


        Intent i = new Intent(getApplicationContext(), MyInfo.class);
        //i.putExtra("locationInfo", position);
        startActivity(i);

    }

    public void tappedMyLogs(View view){
        Log.i("You tapped:", "My Logs");

        Intent i = new Intent(getApplicationContext(), MyLogs.class);
        //i.putExtra("locationInfo", position);
        startActivity(i);

    }

    public void tappedTimer(View view){
        Log.i("You tapped:", "My Timer");

        Intent i = new Intent(getApplicationContext(), Timer.class);
        //i.putExtra("locationInfo", position);
        startActivity(i);
    }

    public void sendSMS(View view){
        Intent i = new Intent(getApplicationContext(), SMS.class);
        //i.putExtra("locationInfo", position);
        startActivity(i);
    }




}


