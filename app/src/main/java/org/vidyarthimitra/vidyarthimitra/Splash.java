package org.vidyarthimitra.vidyarthimitra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    String username;
    boolean stored = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        sharedPreferences = getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        username = sharedPreferences.getString( KEY_USERNAME, "" );
        //Toast.makeText( getApplicationContext(), username, Toast.LENGTH_SHORT ).show();


        if (username != "") {
            Timer timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {

                    Intent intent = new Intent( Splash.this, HomeActivity.class );
                    Bundle b = new Bundle();
                    b.putString( "username", username );
                    intent.putExtras( b );
                    startActivity( intent );

                }

            }, 6000 );
        }
        else {
            Timer timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {

                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);

                }

            }, 6000 );
        }
    }

    }












//    Timer timer = new Timer();
//            timer.schedule( new TimerTask() {
//        @Override
//        public void run() {
//
//            Intent intent = new Intent( Splash.this, HomeActivity.class );
//            startActivity( intent );
//
//        }
//
//    }, 6000 );
//}
//    }