package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
   // SharedPreferences prefs = null;
    SharedPreferences prefs;
 //   public final static String IS_LOGIN = "is_login";
   // Boolean mybol=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefs = getSharedPreferences("com.identifyprotector.identifyprotector", MODE_PRIVATE);


        if (prefs.getBoolean("firstrun", true)) {

            // Do first run stuff here then set 'firstrun' as false
            prefs.edit().putBoolean("firstrun", false).commit();

            // using the following line to edit/commit prefs
            setContentView(R2.layout.activity_main);

            Button imageButton = (Button) findViewById(R2.id.RegisterButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), setting_page2.class);
                    startActivity(i);

                }
            });
            Button imageButton1 = (Button) findViewById(R2.id.firstlogin);
            imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent m = new Intent(getBaseContext(), main_login.class);
                    startActivity(m);

                }
            });


        }
        //if logged in, redirect user to login activity
         else if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            // call Login Activity
            Intent intent = new Intent(this, main_login.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // call verify Activity
           // Intent l = new Intent(getBaseContext(), login.class);
            Intent l = new Intent(getBaseContext(), login.class);
            startActivity(l);
            finish();
        }

        /*  else if (isLoggedIn) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }*/





        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo= null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();


        }


        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.d(TAG, "onCreate: "+"True"+netInfo.getTypeName());
     new PerformSearchTask().execute("sghouzali@ksu.edu.sa");
     //satish.talim@gmail.com
     //d7om.sa@hotmail.com
     //aloeyy@gmail.com
     //alla.alabduljabbar@gmail.com
     //sghouzali@ksu.edu.sa
     //435201016@student.ksu.edu.sa
     //438202039@student.ksu.edu.sa
                  } else {
            Log.d(TAG, "onCreate: "+"False");

        }


    }

    @SuppressLint("StaticFieldLeak")
    protected class PerformSearchTask extends AsyncTask<String, Void, ArrayList<Breach>> {
        protected ArrayList<Breach> doInBackground(String... accounts) {
            HaveIbeenPwnedAPI api = new HaveIbeenPwnedAPI();
            ArrayList<Breach> result = new ArrayList<Breach>();
            try {
                result = api.query(accounts[0]);
            } catch (URISyntaxException e) {
                Toast.makeText(getBaseContext(), getString(R2.string.error_invalid_uri_syntax), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), getString(R2.string.error_invalid_response), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return result;
        }


        protected void onPostExecute(ArrayList<Breach> result) {

            // Create the history card if not already created by FetchHistoryTask

            if(result == null) {
                Toast.makeText(getBaseContext(), getString(R2.string.error_result_null), Toast.LENGTH_SHORT).show();
            }
        else {
                Log.d(TAG, "onPostExecute: "+result.size()+result.get(0).getName());
            }
        }
        }
    }

