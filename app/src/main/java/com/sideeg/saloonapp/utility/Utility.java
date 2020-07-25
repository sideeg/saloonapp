package com.sideeg.saloonapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.ui.activites.LoginActivity;



import java.text.DecimalFormat;

import androidx.appcompat.app.AlertDialog;

public class Utility {
    /**
     * custom method to show alert dialog
     *
     * @param msg:     String to be set as alert dialog title
     * @param title:   String to be displayed as alert dialog message
     * @param context: contains activity context
     */
    public static void showAlertDialog(String title, String msg, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                (dialog, which) -> {
                    if (msg.equals("please login first")) {
                        new LocalSession(context).clearSession();
                        dialog.dismiss();
                        context.startActivity(new Intent(context, LoginActivity.class));

                    }else
                        dialog.dismiss();
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        // show it
        alertDialog.show();

    }

    public static void printLog(String... msg) {
        String str = "";
        for (String i : msg) {
            str = str + "\n" + i;
        }
        Log.i("Orderpharma", str);

    }

    public static String getRoundedPrice(String price){
        try {
            double thePrice = Double.parseDouble(price);
            return new DecimalFormat("0.#").format(Math.round(thePrice * 100.0) / 100.0);
        }catch (Exception e){
            return price;
        }
    }

    public static ProgressDialog GetProcessDialog(Activity activity) {
        // prepare the dialog box
        ProgressDialog dialog = new ProgressDialog(activity, 5);
        // make the progress bar cancelable
        dialog.setCancelable(true);
        // set a message text
        dialog.setMessage("Loading...");

        // show it
        return dialog;
    }
}
