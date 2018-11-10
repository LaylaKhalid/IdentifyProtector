package com.identifyprotector.identifyprotector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Supdate extends AppCompatActivity {
//192.168.43.235
    private static final String URL_UPSetting = "http://192.168.100.14/loadSetting.php?UserID=";
    private String uname;
    List<setting> SettingList;
    //setting SettingList;
    private String duration;
    private String ActivateAlert="";
    private RadioButton d;
    private RadioButton w;
    private RadioButton m;
    private CheckBox check1;
    private String ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.update_setting);
        //addListnerToCheckBox();


        Button imageButton = (Button) findViewById(R2.id.upstting);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListnerToCheckBox();

            }
        });


        Button logout = (Button) findViewById(R2.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Supdate.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(Supdate.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(Supdate.this);
                                Intent intent = new Intent(Supdate.this, main_login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });


        //initializing the productlist
        SettingList = new ArrayList<>();
        uname = SaveSharedPreference.getUserName(Supdate.this).toString();
        //this method will fetch and parse json
        //to display it in recyclerview
        loadlogins();

    }



    private void loadlogins() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_UPSetting+uname,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                       //         ID=product.getString("ID");
                        //        duration=product.getString("duration");
                         //       ActivateAlert=product.getString("ActivateAlert");

                                SettingList.add(new setting(
                                        product.getString("ID"),
                                        product.getString("duration"),
                                        product.getString("ActivateAlert")
                                ));
                            //    duration=SettingList.get(i).getDuration();

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

      //  duration=SettingList.get(1).getDuration();


        // CheckValue();
     //   Toast.makeText(getBaseContext(), duration, Toast.LENGTH_SHORT).show();

    }

    public void CheckValue() {
        Toast.makeText(getBaseContext(), duration, Toast.LENGTH_SHORT).show();

        check1 = (CheckBox) findViewById(R2.id.mybox);
        if(ActivateAlert.equals("1")){
          check1.setChecked(true);
          } else {
            check1.setChecked(false);
        }

        d = (RadioButton) findViewById(R2.id.daily);
        w = (RadioButton) findViewById(R2.id.weekly);
        m = (RadioButton) findViewById(R2.id.monthly);

        if (duration.equals("1")) {
            d.setChecked(true);
        } else if  (duration.equals("2")) {
            w.setChecked(true);
        } else if (duration.equals("3")) {
            m.setChecked(true);
        }



    }

    public void addListnerToCheckBox() {


        check1 = (CheckBox) findViewById(R2.id.mybox);
        check1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "is checked", Toast.LENGTH_SHORT).show();
                            ActivateAlert = "1";

                        } else {
                            ActivateAlert = "0";
                        }
                    }
                }
        );
        d = (RadioButton) findViewById(R2.id.daily);
        if (d.isChecked()) {
            duration = "1";
        }
        d.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "daily is checked", Toast.LENGTH_SHORT).show();
                            duration = "1";

                        }
                    }
                }
        );

        w = (RadioButton) findViewById(R2.id.weekly);
        w.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "weekly is checked", Toast.LENGTH_SHORT).show();
                            duration = "2";

                        }
                    }
                }
        );


        m = (RadioButton) findViewById(R2.id.monthly);
        m.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "monthly is checked", Toast.LENGTH_SHORT).show();
                            duration = "3";

                        }
                    }
                }
        );


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Warning")
                .setMessage("Are you sure you want to leave this page ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        //finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
