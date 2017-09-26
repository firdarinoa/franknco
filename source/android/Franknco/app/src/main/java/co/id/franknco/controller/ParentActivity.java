package co.id.franknco.controller;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by GSS-NB-2016-0012 on 8/26/2017.
 */

public class ParentActivity extends AppCompatActivity {
    public SessionManager session;
    public Function function;
    final Handler ha = new Handler();
    public static String aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session manager
        session = new SessionManager(getApplicationContext());
        function = new Function(this);
        aa = session.getTokenId();
        verification_token();
    }

    public void verification_token() {

        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function

                function.VerifikasiToken(session.getTokenId(),GetUrlName.authorization+session.getTokenId());
                ha.postDelayed(this, 10000);
            }
        }, 10000);
    }

    public void stop_verification_token(){
        ha.removeCallbacksAndMessages(null);
    }


    @Override
    protected void onPause() {
        super.onPause();
        stop_verification_token();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop_verification_token();
    }

}

