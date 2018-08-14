package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPassword extends AppCompatActivity {
    EditText email_reset;
    Button forgot_button;
    int forgotchecker = 0;
    TextView go_to_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email_reset = (EditText) findViewById(R.id.reset_email_edittext);
        forgot_button = (Button) findViewById(R.id.reset_pass_btn);
        go_to_Login = (TextView)findViewById(R.id.go_to_login);
        go_to_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResetPassword.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    public void backClick() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void forgotvalidate() {
        String forgot_str_email = email_reset.getText().toString();
        if (forgot_str_email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(forgot_str_email).matches())) {
            email_reset.setError( "Invalid email" );
            email_reset.requestFocus();
            forgotchecker = 1;
        }
        else {
            forgotchecker = 1;
        }
    }

    public void onForgot(View view) {
        String forgot_str_email = email_reset.getText().toString();

        forgotvalidate();

        if (forgotchecker == 1) {
            String type = "forgot";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, forgot_str_email);
        }
    }
}