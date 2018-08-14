package org.vidyarthimitra.vidyarthimitra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {

    EditText username, contact, email, password, confirm_password;
    Button signup;
    TextView clickon_signin, click_terms;
    CheckBox check;
    ImageView back_img;
    private ProgressDialog progressDialog;
    private AppCompatCheckBox checkbox, checkboxcnf;
    int checker = 1 ;
    boolean value = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_edittext);
        contact = findViewById(R.id.contact_edittext);
        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
        confirm_password = findViewById(R.id.confirm_edittext);
        clickon_signin = (TextView) findViewById(R.id.clickonsignin);
        back_img = (ImageView) findViewById(R.id.backimg);

        click_terms = findViewById(R.id.termsclick);

        signup = (Button) findViewById(R.id.signupbutton);

        check = (CheckBox) findViewById(R.id.checkbox);

        checkbox = (AppCompatCheckBox) findViewById(R.id.password_checkbox);
        checkboxcnf = (AppCompatCheckBox)findViewById(R.id.password_checkbox2);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    value = true;
                } else {
                    value = false;

                }
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        checkboxcnf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        clickon_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        click_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Terms.class));
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }



    public void validation() {
        String emailPattern = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
                "            + \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
        String phonePattern = "(0/91)?[7-9][0-9]{9}";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";

        String str_email = email.getText().toString();
        if (str_email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
            email.setError("invalid email");
            email.requestFocus();
            checker = 0;
        }
        /*else {
            checker = 1;
        }*/
        String str_username = username.getText().toString();
        if (str_username.length() == 0) {
            username.setError("Invalid username");
            username.requestFocus();
            checker = 0;
        } else {
            checker = 1;
        }
        String str_contact = contact.getText().toString();
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(str_contact);
        if (!m.matches()) {
            checker = 0;
            contact.setError("Invalid contact number");
            contact.requestFocus();
        }


        /*else {

            checker = 1;
        }*/
        String str_password = password.getText().toString();
        String str_confirm_password = confirm_password.getText().toString();
        if (str_password.isEmpty()) {
            password.setError("Invalid Password");
            password.requestFocus();
            checker = 0;
        }
        /*else {
            checker = 1;
        }*/
        if (!str_confirm_password.equals(str_password)) {
            confirm_password.setError("Pass");
            confirm_password.requestFocus();
            checker = 0;
        }
        /*else {

            checker = 1;
        }*/

        /*if(str_contact.matches(phonePattern) || str_contact.length()==0){
            contact.setError("Invalid contact number");
            contact.requestFocus();
        }*/

        /*String str_pass = password.getText().toString();
        if (str_pass.matches(passwordPattern) || str_pass.length() == 0) {
            password.setError("Password should contain number, special characters, uppercase and lowercase letters");
            password.requestFocus();
        }

        String str_confirm_pass = confirm_password.getText().toString();
        if (str_confirm_pass.equals(str_pass)){
            confirm_password.setError("Password does not match");
            confirm_password.requestFocus();
        }*/
       /*String str_pass = password.getText().toString();
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(str_pass);
        if(matcher.matches()){
            checker = 1;
        }
        else {
            password.setError("invalid password");
            password.requestFocus();
            checker = 0;
        }
*/

    }

    public void onReg(View view) {

        String str_username = username.getText().toString();
        String str_contact = contact.getText().toString();
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        String str_confirm_password = confirm_password.getText().toString();
       /* String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(str_username.length() == 0) {
            username.setError("Invalid Username");
            username.requestFocus();
            //finish();
        }
        if(str_email.matches(emailPattern) || str_email.length() == 0) {
            email.setError("Invalid Email");
            email.requestFocus();

        }*/
        validation();
        if (checker == 1) {
            if (value == true) {
                // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                String type = "register";
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, str_username, str_email, str_contact, str_password, str_confirm_password);
            } else {
                Toast.makeText(getApplicationContext(), "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
