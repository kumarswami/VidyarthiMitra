package org.vidyarthimitra.vidyarthimitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class change_pass extends AppCompatActivity {
    EditText change_password, change_new_password;
    Button changebutton;

    CheckBox check;
    private AppCompatCheckBox checkBox;
    boolean value = false;
    String mailcheck;
    int pchecker, newpchecker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Bundle bundle = getIntent().getExtras();
        mailcheck = bundle.getString("mail");
        change_password = findViewById(R.id.change_password_edittext);
        change_new_password = findViewById(R.id.change_confirm_edittext);
        changebutton = (Button) findViewById( R.id.changebutton);
        checkBox = (AppCompatCheckBox)findViewById( R.id.change_password_checkbox );
        check = (CheckBox)findViewById( R.id.password_checkbox );
        checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!isChecked) {
                            change_password.setTransformationMethod( PasswordTransformationMethod.getInstance() );
                        }
                        else {
                            change_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance());
                        }
                    }
                } );


    }
    public void validate() {
        String str_password = change_password.getText().toString();
        String str_new_password = change_new_password.getText().toString();
    if (str_password.isEmpty()) {
        change_password.setError("Invalid Password");
        change_password.requestFocus();
        pchecker = 0;
    } else {
        pchecker = 1;
    }
    if (str_new_password.isEmpty()) {
            change_new_password.setError("Invalid Password");
            change_new_password.requestFocus();
            newpchecker = 0;
        } else {
            newpchecker = 1;
        }}
    public void backClick(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
    public void onchangepassword(View view) {
            String password = change_password.getText().toString();
            String newpassword = change_new_password.getText().toString();
            validate();
            if(pchecker == 1 && newpchecker == 1) {
                String type = "change";
                BackgroundWorker backgroundWorker = new BackgroundWorker( this );
                backgroundWorker.execute( type, mailcheck, password, newpassword );
            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter correct password", Toast.LENGTH_SHORT).show();
            }

    }
}
