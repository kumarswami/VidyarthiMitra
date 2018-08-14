package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Profile_activity extends AppCompatActivity {
    TextView t, mymail, mycontact;
    String text;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);





        Bundle bundlel = getIntent().getExtras();
        String res = bundlel.getString("result");
        String text = bundlel.getString("username");
        String[] parts = res.split("/");



        t=(TextView)findViewById(R.id.iname);
       // String name=getIntent().getExtras().getString("username");
        t.setText(parts[0]);

        mymail = (TextView)findViewById(R.id.mymail);
        mymail.setText(text);
        mycontact = (TextView)findViewById(R.id.mycontact);
        mycontact.setText(parts[1]);

        String password=getIntent().getExtras().getString("password");


    }
    public  void LogOut(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        new User(Profile_activity.this).remove();
        Intent intent=new Intent(Profile_activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void backClick(View view){
        Intent i=new Intent(Profile_activity.this,HomeActivity.class);
        startActivity(i);

    }
    public void change_pass(View view){

        Bundle b = new Bundle();
        String mail = getIntent().getExtras().getString( "username" );
        b.putString( "mail", mail );
        Intent intent=new Intent(Profile_activity.this,change_pass.class);
        intent.putExtras(b);
        startActivity(intent);

    }
}
