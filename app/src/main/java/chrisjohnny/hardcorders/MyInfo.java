package chrisjohnny.hardcorders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class MyInfo extends AppCompatActivity {

    final String ACCOUNT_SID = "AC782da9556d8115bd6ea3259fb651787c";
    final String AUTH_TOKEN = "c849a64bcf234c8bc463d366da1de131";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build the parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+5149513273"));
        params.add(new BasicNameValuePair("From", "+14387938713"));
        params.add(new BasicNameValuePair("Body", "Hey Jenny! Good luck on the bar exam!"));
        params.add(new BasicNameValuePair("MediaUrl", "http://farm2.static.flickr.com/1075/1404618563_3ed9a44a3a.jpg"));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = null;
        try {
            message = messageFactory.create(params);
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        System.out.println(message.getSid());
    }
}
