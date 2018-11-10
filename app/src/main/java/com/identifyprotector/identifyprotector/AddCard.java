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

public class AddCard extends AppCompatActivity {

    private EditText Nickname, CreditNum, SecureCode, CardOwner,ExpDate,PhoneNum,Country,City,Street,ZipCode;
    private String Nkname, CardNum, SecCode, Owner,ExpD,PhoneNumber,CountryX,CityX,StreetX,ZipCodeX;
    private String encyCreditNum, encySecureCode,encyStreet,encyZipCode;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    String TAG = "AddCard";
    String AES="AES";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.creditcard_iterface);

        Nickname = (EditText)findViewById(R2.id.profilecard);
        CreditNum = (EditText)findViewById(R2.id.CreditNum);
        SecureCode = (EditText)findViewById(R2.id.SecureCode);
        CardOwner = (EditText)findViewById(R2.id.CardOwner);
        ExpDate = (EditText)findViewById(R2.id.ExpDate);
        PhoneNum = (EditText)findViewById(R2.id.PhoneNum);
        Country = (EditText)findViewById(R2.id.Country);
        City = (EditText)findViewById(R2.id.City);
        Street = (EditText)findViewById(R2.id.Street);
        ZipCode = (EditText)findViewById(R2.id.ZipCode);

        allowbox = (CheckBox) findViewById(R2.id.allowcard);
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
        Button imageButton = (Button) findViewById(R2.id.addcard);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nkname=Nickname.getText().toString();
                CardNum=CreditNum.getText().toString();
                SecCode=SecureCode.getText().toString();
                Owner=CardOwner.getText().toString();
                ExpD=ExpDate.getText().toString();
                PhoneNumber=PhoneNum.getText().toString();
                CountryX=Country.getText().toString();
                CityX=City.getText().toString();
                StreetX=Street.getText().toString();
                ZipCodeX=ZipCode.getText().toString();

                try {
                    encyCreditNum=encrypt(CardNum,TAG);
                    encySecureCode=encrypt(SecCode,TAG);
                    encyStreet=encrypt(StreetX,TAG);
                    encyZipCode=encrypt(ZipCodeX,TAG);
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
                    builder = new AlertDialog.Builder(AddCard.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddCard.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddCard.this);
                                Intent intent = new Intent(AddCard.this, main_login.class);
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




        if (Nkname.matches("")) {
            Toast.makeText(this, "You did not enter a Profile name", Toast.LENGTH_SHORT).show();
        } else if (CardNum.matches("")) {
            Toast.makeText(this, "You did not enter a Credit Card Number", Toast.LENGTH_SHORT).show();
        } else if (Owner.matches("")) {
            Toast.makeText(this, "You did not enter a Card Owner", Toast.LENGTH_SHORT).show();
        } else if (ExpD.matches("")) {
            Toast.makeText(this, "You did not enter a Expiration Date", Toast.LENGTH_SHORT).show();
        }  else if (CountryX.matches("")) {
            Toast.makeText(this, "You did not enter a Country", Toast.LENGTH_SHORT).show();
        } else if (CityX.matches("")) {
            Toast.makeText(this, "You did not enter a City", Toast.LENGTH_SHORT).show();
        } else if (StreetX.matches("")) {
            Toast.makeText(this, "You did not enter a Street", Toast.LENGTH_SHORT).show();
        } else if (ZipCodeX.matches("")) {
            Toast.makeText(this, "You did not enter a Zip Code", Toast.LENGTH_SHORT).show();
        }
        else {


            uname = SaveSharedPreference.getUserName(AddCard.this).toString();
            String type = "Card";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, Nkname, ActivateMonitoring, encyCreditNum, encySecureCode,
                    Owner, ExpD, PhoneNumber, CountryX,CityX, encyStreet, encyZipCode, uname);
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
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
