package org.vidyarthimitra.vidyarthimitra;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AppCompatCheckBox checkboxpass;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    EditText UsernameEt,PasswordEt;
    TextView click_forgot,click_signup;
    Button signin;
    int logchecker, logpchecker = 0;
    public static String Resultt;
    private TextView textView;
    private ProgressDialog progressDialog;
    int check = 0;
    String myusername, mypassword;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences( PREF_NAME, Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();
        UsernameEt = findViewById(R.id.email_edittext);
        PasswordEt = findViewById(R.id.password_edittext);

        click_forgot = findViewById(R.id.resetps);
        click_signup = findViewById(R.id.clickonsignup);
        click_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        signin = (Button)findViewById(R.id.signinbutton);

        myusername = sharedPreferences.getString( KEY_USERNAME, "" );
        mypassword = sharedPreferences.getString( KEY_PASS, "");

        //UsernameEt.setText(myusername);
        //PasswordEt.setText(mypassword);


        textView = (TextView) findViewById(R.id.contact);
        checkboxpass =(AppCompatCheckBox)findViewById(R.id.password_checkbox3);
        checkboxpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    PasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    PasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Contactus.class);
                startActivity(i);

            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if(netinfo != null && netinfo.isConnected()) {

        }
        else {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_SHORT).show();
        }
        click_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });


    }

    public void loginvalidate() {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        if (username.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(username).matches())) {
            UsernameEt.setError("invalid email");
            UsernameEt.requestFocus();
            logchecker = 0;
        }
        else {
            logchecker = 1;
        }
        if(password.isEmpty()) {
            PasswordEt.setError("Invalid Password");
            PasswordEt.requestFocus();
            logpchecker = 0;
        }
        else {
            logpchecker = 1;
        }
    }

    public void onLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        loginvalidate();
        if(logchecker == 1 && logpchecker == 1) {

            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, username, password);


        }

    }






    public void click(){



        Intent intent=new Intent(MainActivity.this,Profile_activity.class);
        startActivity(intent);
    }




    public void openReg(View view){
        startActivity(new Intent(this,Register.class));
    }
    class BackgroundWorker extends AsyncTask<String ,Void,String> {

        Context context;
        AlertDialog alertDialog;

        public BackgroundWorker(Context ctx){
            context = ctx;


        }
        @Override
        public String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://vidyarthimitra.org/phpmailer/login.php";
            String register_url = "http://vidyarthimitra.org/phpmailer/reg.php";
            String forgot_url = "http://vidyarthimitra.org/phpmailer/forgot.php";
            String profile_url = "http://vidyarthimitra.org/phpmailer/profile.php";
            if(type.equals("login")){

                try {
                    String email = params[1];
                    String password = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine())!=null){
                        result += line;

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(type.equals("profile")){

                try {
                    String email = params[1];

                    URL url = new URL(profile_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine())!=null){
                        result += line;

                    }
                    Intent intent = new Intent(context, Profile_activity.class );
                    Bundle b = new Bundle();
                    b.putString("result", result);
                    b.putString( "username", email);
                    intent.putExtras(b);
                    startActivity(intent);


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(type.equals("forgot")){

                try {
                    String email = params[1];
                    URL url = new URL(forgot_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine())!=null){
                        result += line;

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else  if(type.equals("register")){

                try {
                    String user_name = params[1];
                    String email = params[2];
                    String contact = params[3];
                    String password = params[4];
                    String birthday = params[5];

                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                            +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                            +URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine())!=null){
                        result += line;

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
            progressDialog = Utils.showProgressBar(MainActivity.this,"Please Wait...");

        }



        @Override
        protected void onPostExecute(String result){
            if (result != null) {
                if (result.contains("Login Success")) {
                    Utils.hideProgressBar(progressDialog);
                    String username = UsernameEt.getText().toString();
                    // String password = PasswordEt.getText().toString();
                    User user = new User(MainActivity.this);
                    user.setUsername(username);
                    // user.setPassword( password );
                    editor.putString( KEY_USERNAME, UsernameEt.getText().toString());
                    editor.putString( KEY_PASS, PasswordEt.getText().toString() );
                    editor.putBoolean( KEY_REMEMBER, true );
                    editor.apply();

                    //String type = "profile";
                    //BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                    // backgroundWorker.execute(type, username);

                    Intent intent = new Intent(context, HomeActivity.class);

                    Bundle b = new Bundle();
                    b.putString("username", username);
                    intent.putExtras(b);

//                intent.putExtra("password", password);
                    startActivity(intent);





     /*           progressBar.setMax(40);
                progressBar.setProgress(0);
                final Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i <= 10; i++){
                                progressBar.setProgress(i);
                                sleep(20);


                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();

                        }
                        finally {
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                };
                thread.start();*/
                }
//                if(result == "New record created successfully") {
//                    Utils.hideProgressBar(progressDialog);
//                    String username = UsernameEt.getText().toString();
//                    // String password = PasswordEt.getText().toString();
//                    User user = new User(MainActivity.this);
//                    user.setUsername(username);
//                    // user.setPassword( password );
//                    editor.putString( KEY_USERNAME, UsernameEt.getText().toString());
//                    editor.putString( KEY_PASS, PasswordEt.getText().toString() );
//                    editor.putBoolean( KEY_REMEMBER, true );
//                    editor.apply();
//
//                    //String type = "profile";
//                    //BackgroundWorker backgroundWorker = new BackgroundWorker(context);
//                    // backgroundWorker.execute(type, username);
//
//                    Intent intent = new Intent(context, HomeActivity.class);
//
//                    Bundle b = new Bundle();
//                    b.putString("username", username);
//                    intent.putExtras(b);
//
////                intent.putExtra("password", password);
//                    startActivity(intent);
//                }
            }
            //else if (result != null){
            //  if (result.contains("Signup Success")) {
            //    Utils.hideProgressBar(progressDialog);
            //}
            else {
                Toast.makeText(getApplicationContext(),"Can not login.Network error",Toast.LENGTH_LONG).show();
            }






        }






        // alertDialog.show();
        //}

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
