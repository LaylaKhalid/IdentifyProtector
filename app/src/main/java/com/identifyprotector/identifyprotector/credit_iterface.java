package com.identifyprotector.identifyprotector;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

public class credit_iterface extends AppCompatActivity {

    private Button logout;
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work

    private static final String URL_Credit = "http://192.168.100.14/creditcard.php?UserID=";
    //private static final String URL_Credit = "http://192.168.43.235/creditcard.php?UserID=";
    private String uname;
    //a list to store all the products
    List<creditcard> creditList;

    //the recyclerview
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.credit_iterface);

        logout = (Button) findViewById(R2.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(credit_iterface.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(credit_iterface.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(credit_iterface.this);
                                Intent intent = new Intent(credit_iterface.this, main_login.class);
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
        ImageButton imageButton = (ImageButton) findViewById(R2.id.addpro);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddCard.class);
                startActivity(i);
                finish();

            }
        });


        //getting the recyclerview from xml
        recyclerView = findViewById(R2.id.recylcerViewc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        creditList = new ArrayList<>();
        uname = SaveSharedPreference.getUserName(credit_iterface.this).toString();
        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Credit+uname,
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


                                creditList.add(new creditcard(
                                        product.getString("ID"),
                                        product.getString("Nickname"),
                                        product.getString("ActivateMonitoring"),
                                        product.getString("CreditNum"),
                                        product.getString("SecureCode"),
                                        product.getString("CardOwner"),
                                        product.getString("ExpDate"),
                                        product.getString("PhoneNum"),
                                        product.getString("Country"),
                                        product.getString("City"),
                                        product.getString("Street"),
                                        product.getString("ZipCode")
                                ));
                            }


                            //creating adapter object and setting it to recyclerview
                            creditAdapter  adapter = new creditAdapter(credit_iterface.this, creditList);
                            recyclerView.setAdapter(adapter);
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
    }




}
