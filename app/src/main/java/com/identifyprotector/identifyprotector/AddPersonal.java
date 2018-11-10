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

public class AddPersonal extends AppCompatActivity {

    private EditText Nickname, FirstName, MiddleName, LastName,DOB,NationalID,PassportNumber,mail,PhoneNum,Country,	City,Street,ZipCode;
    private String Nkname, FirstN, MiddleN, LastN, DOBp, NationalIDp, PassportNum, email, Phone, Countryp, Cityp, Streetp, ZipCodep;
    private String encyNationalID, encyPassportNum,encyemail,encyPhone,encyStreet,encyZipCode;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    String TAG = "AddPersonal";
    String AES="AES";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.personal_information_interface);

        Nickname = (EditText)findViewById(R2.id.AddPerson);
        FirstName = (EditText)findViewById(R2.id.FirstN);
        MiddleName = (EditText)findViewById(R2.id.MiddleN);
        LastName = (EditText)findViewById(R2.id.LastN);
        DOB = (EditText)findViewById(R2.id.DOB);
        NationalID = (EditText)findViewById(R2.id.NationalID);
        mail = (EditText)findViewById(R2.id.email);
        PhoneNum = (EditText)findViewById(R2.id.Phonepnum);
        PassportNumber = (EditText)findViewById(R2.id.PassportNum);
        Country = (EditText)findViewById(R2.id.Countryp);
        City = (EditText)findViewById(R2.id.Cityp);
        Street = (EditText)findViewById(R2.id.Streetp);
        ZipCode = (EditText)findViewById(R2.id.ZipCodep);

        allowbox = (CheckBox) findViewById(R2.id.allowpesonal);
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


        Button imageButton = (Button) findViewById(R2.id.addPersonal);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nkname=Nickname.getText().toString();
                FirstN=FirstName.getText().toString();
                MiddleN=MiddleName.getText().toString();
                LastN=LastName.getText().toString();
                DOBp=DOB.getText().toString();
                NationalIDp=NationalID.getText().toString();
                email=mail.getText().toString();
                PassportNum=PassportNumber.getText().toString();
                Phone=PhoneNum.getText().toString();
                Countryp=Country.getText().toString();
                Cityp=City.getText().toString();
                Streetp=Street.getText().toString();
                ZipCodep=ZipCode.getText().toString();

                try {
                    encyNationalID=encrypt(NationalIDp,TAG);
                    encyPassportNum=encrypt(PassportNum,TAG);
                    encyemail=encrypt(email,TAG);
                    encyPhone=encrypt(Phone,TAG);
                    encyStreet=encrypt(Streetp,TAG);
                    encyZipCode=encrypt(ZipCodep,TAG);
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
                    builder = new AlertDialog.Builder(AddPersonal.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddPersonal.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddPersonal.this);
                                Intent intent = new Intent(AddPersonal.this, main_login.class);
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
        } else if (FirstN.matches("")) {
            Toast.makeText(this, "You did not enter a First Name", Toast.LENGTH_SHORT).show();
        } else if (LastN.matches("")) {
            Toast.makeText(this, "You did not enter a Last Name", Toast.LENGTH_SHORT).show();
        }else if (email.matches("")) {
            Toast.makeText(this, "You did not enter an Email", Toast.LENGTH_SHORT).show();
        } else if (Phone.matches("")) {
            Toast.makeText(this, "You did not enter a Phone Number", Toast.LENGTH_SHORT).show();
        }   else if (Countryp.matches("")) {
            Toast.makeText(this, "You did not enter a Country", Toast.LENGTH_SHORT).show();
        } else if (Cityp.matches("")) {
            Toast.makeText(this, "You did not enter a City", Toast.LENGTH_SHORT).show();
        } else if (Streetp.matches("")) {
            Toast.makeText(this, "You did not enter a Street", Toast.LENGTH_SHORT).show();
        } else if (ZipCodep.matches("")) {
            Toast.makeText(this, "You did not enter a Zip Code", Toast.LENGTH_SHORT).show();
        }
        else {

            uname = SaveSharedPreference.getUserName(AddPersonal.this).toString();
            String type = "Personal";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, Nkname, ActivateMonitoring, FirstN, MiddleN,
                    LastN, DOBp, encyNationalID,encyPassportNum,encyemail,encyPhone, Countryp,Cityp, encyStreet, encyZipCode, uname);
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
