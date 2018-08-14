package org.vidyarthimitra.vidyarthimitra;
import android.content.Context;
import android.content.SharedPreferences;

public class
User {
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public  void  remove(){
        sharedPreferences.edit().clear().commit();
    }



    private String username;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        sharedPreferences.getString("userdata","");
        return  username;
    }



    public void setUsername(String username) {
        this.username = username;
        sharedPreferences.edit().putString("userdata",username).commit();

    }

    public String getPassword() {
       sharedPreferences.getString("userda","");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        sharedPreferences.edit().putString("userda",password).commit();
    }

    private String password;

    public User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",context.MODE_PRIVATE);

    }

}
