package chrisjohnny.hardcorders;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "hardcoders123md21";
    public static final String YOUR_CLIENT_KEY = "hc123d21";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.enableLocalDatastore(this);


        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("hardcoders123md21")
                        .clientKey(null)
                        .server("http://gymme-android.herokuapp.com/parse/")
                        .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("appinfo", "good");
                } else {
                    Log.d("appinfo", "bad");
                }
            }
        });
        // ...
    }
}