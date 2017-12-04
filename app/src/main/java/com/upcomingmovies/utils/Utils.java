package com.upcomingmovies.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Shuhab abs-pc-2f-28 on 4/12/17.
 */

public class Utils {
    public ProgressDialog progressDialog;

    public void showProgressDialog(Context context, String msg) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
