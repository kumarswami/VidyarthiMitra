package org.vidyarthimitra.vidyarthimitra;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static ProgressDialog showProgressBar(Context mContext,String title){
        ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle(title);
        progressBar.show();
        return progressBar;
    }
    public static void hideProgressBar(ProgressDialog progressBar){
        if(progressBar !=null)
        {
            if(progressBar.isShowing()) {
                progressBar.dismiss();
            }
            progressBar = null;
        }
    }
}
