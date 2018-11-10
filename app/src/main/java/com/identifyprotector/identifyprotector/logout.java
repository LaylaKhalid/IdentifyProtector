package com.identifyprotector.identifyprotector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

public class logout {

    Context context;
    AlertDialog alertDialog;

  /*  public void logout(Context ctx){
        context=ctx;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Notification :");
        alertDialog.setIcon(android.R2.drawable.ic_dialog_alert);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        //finish();
                        //finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }*/
}
