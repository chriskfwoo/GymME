package chrisjohnny.hardcorders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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




}


