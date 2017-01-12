package chrisjohnny.hardcorders;

import android.net.Uri;
import com.nuance.speechkit.PcmFormat;



public class Configuration {

    //All fields are required.
    //Your credentials can be found in your Nuance Developers portal, under "Manage My Apps".
    public static final String APP_KEY = "9ae68f0746bacf1d8c487b17290e6ea1dfc3881603e44c2d5ec68fe4f6022390e9b2982f6c674df8afc80fd9d67d7ab54102cc44e7187245de0129d4044c65ed";
    public static final String APP_ID = "NMDPTRIAL_johnnyinteriano_hotmail_com20160220124420";
    public static final String SERVER_HOST = "nmsps.dev.nuance.com";
    public static final String SERVER_PORT = "443";

    public static final Uri SERVER_URI = Uri.parse("nmsps://" + APP_ID + "@" + SERVER_HOST + ":" + SERVER_PORT);

    //Only needed if using NLU
    public static final String CONTEXT_TAG = "M1722_A910_V2";

    public static final PcmFormat PCM_FORMAT = new PcmFormat(PcmFormat.SampleFormat.SignedLinear16, 16000, 1);



}
