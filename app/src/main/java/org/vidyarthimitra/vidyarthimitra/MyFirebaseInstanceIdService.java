package org.vidyarthimitra.vidyarthimitra;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private  static  final String REG_TOKEN="REG_TOKEN";




    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        //store this token in preferences

        Log.d(REG_TOKEN,recent_token);
    }
}
