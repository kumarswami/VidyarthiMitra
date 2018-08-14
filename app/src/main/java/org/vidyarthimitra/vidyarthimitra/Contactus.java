package org.vidyarthimitra.vidyarthimitra;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Contactus extends AppCompatActivity {
    TextView callone, calltwo;
    ImageView call1, call2, email1, email2, map;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_contactus );
            callone = (TextView)findViewById( R.id.ph1);
            calltwo = (TextView)findViewById( R.id.ph2 );
            call1 = (ImageView) findViewById( R.id.call_one );
            call2 = (ImageView) findViewById( R.id.call_two);
            email1 = (ImageView) findViewById( R.id.email_one );
            email2 = (ImageView) findViewById( R.id.email_two );
            map = (ImageView)findViewById(R.id.map);
        }
        public void oncall1(View view){
            Intent intent = new Intent( Intent.ACTION_DIAL );
            intent.setData( Uri.parse( "tel:" + "+917720025900" ) );
            startActivity( intent );
    }
        public void oncall2(View view){
            Intent intent = new Intent( Intent.ACTION_DIAL );
            intent.setData( Uri.parse( "tel:" + "+917720081400" ) );
            startActivity( intent );
        }
        public void onemailcon(View view){
            try {
                Intent emailintent = new Intent( Intent.ACTION_SEND );
                emailintent.putExtra( Intent.EXTRA_EMAIL, new String[]{"contact@vidyarthimitra.org"} );
                emailintent.setType( "message/rfc822" );
                startActivity( Intent.createChooser( emailintent, "Choose Email Client" ) );
            }
            catch (ActivityNotFoundException info) {
                Toast.makeText( getApplicationContext(), "Sorry not client found", Toast.LENGTH_SHORT );
            }
        }
        public void onemailinfo(View view){
            try {
                Intent emailintent = new Intent( Intent.ACTION_SEND );
                emailintent.putExtra( Intent.EXTRA_EMAIL, new String[]{"info@vidyarthimitra.org"} );
                emailintent.setType( "message/rfc822" );
                startActivity( Intent.createChooser( emailintent, "Choose Email Client" ) );
            }
            catch (ActivityNotFoundException info) {
                Toast.makeText( getApplicationContext(), "Sorry not client found", Toast.LENGTH_SHORT );
            }
        }
        public void onmap(View view) {
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode("1/A-4, 1st Floor Fountainhead Aptt G A Kulkarni Path, (Opp. Starbucks & Near, Kothrud, Pune, Maharashtra 411038"));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
}


