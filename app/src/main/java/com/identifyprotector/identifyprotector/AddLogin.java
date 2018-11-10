package com.identifyprotector.identifyprotector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AddLogin extends AppCompatActivity {

    private EditText Profilename, username, password, appname;
    private String filename="";
    private String user_name="";
    private String pass="";
    private String Appname;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    private String encyUser="";
    private String encyPass="";
    //private enc_dec EncDec;
    String TAG = "AddLogin";
    String AES="AES";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.login_credentials_interface);
        Profilename = (EditText)findViewById(R2.id.profile);
        username = (EditText)findViewById(R2.id.name);
        password = (EditText)findViewById(R2.id.pass);
        appname = (EditText)findViewById(R2.id.appname);
        allowbox = (CheckBox) findViewById(R2.id.allow);
        allowbox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "is checked", Toast.LENGTH_SHORT).show();
                            ActivateMonitoring = "1";

                        } else {
                            ActivateMonitoring = "0";
                        }
                    }
                }
        );
       Button imageButton = (Button) findViewById(R2.id.addlogin);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckValid();

                user_name = username.getText().toString();
                pass = password.getText().toString();
                filename = Profilename.getText().toString();
                Appname=appname.getText().toString();

                try {
                    encyUser=encrypt(user_name,TAG);
                    encyPass=encrypt(pass,TAG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               CheckValid();
                finish();
            }
        });


        Button logout = (Button) findViewById(R2.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(AddLogin.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddLogin.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddLogin.this);
                                Intent intent = new Intent(AddLogin.this, main_login.class);
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

    }

    public void CheckValid() {
        //user_name = username.getText().toString();
       // pass = password.getText().toString();
      //  filename = Profilename.getText().toString();
     //   Appname=appname.getText().toString();

        if (filename.matches("")) {
            Toast.makeText(this, "You did not enter a Profile name", Toast.LENGTH_SHORT).show();
        } else if (user_name.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        } else if (pass.matches("")) {
            Toast.makeText(this, "You did not enter a  password", Toast.LENGTH_SHORT).show();
        } else if (Appname.matches("")) {
            Toast.makeText(this, "You did not enter a  App name", Toast.LENGTH_SHORT).show();
        } else {

            uname = SaveSharedPreference.getUserName(AddLogin.this).toString();
            String type = "credential";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, filename,Appname, ActivateMonitoring,encyUser, encyPass,uname );
        }
    }


    private String encrypt(String Data, String password)throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(Data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;

    }

    private SecretKeySpec generateKey(String password)throws Exception {
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte bytes[]=password.getBytes("UTF-8");
        digest.update(bytes, 0,bytes.length);
        byte key[]=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;

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
