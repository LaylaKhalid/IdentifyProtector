package com.identifyprotector.identifyprotector;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class edit_credit extends AppCompatActivity {

    private EditText Nickname, CreditNum, SecureCode, CardOwner,ExpDate,PhoneNum,CountryE,CityE,StreetE,ZipCodeE;
    private String Nkname, CardNum, SecCode, Owner,ExpD,PhoneNumber,CountryX,CityX,StreetX,ZipCodeX;
    private String UPNkname, UPCardNum, UPSecCode, UPOwner,UPExpD,UPPhoneNumber,UPCountryX,UPCityX,UPStreetX,UPZipCodeX;
    private String uname ="";
    private String ID;
    private CheckBox allowbox;
    private String ActivateMonitoring="";
    String AES="AES";
    private String encyCreditNum, encySecureCode,encyStreet,encyZipCode;
    String TAG = "AddCard";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.edit_creditcard_iterface);
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


        try {
            getIncomingIntent();
        }  catch ( IOException e) {
        }


        Button update = (Button) findViewById(R2.id.updateCard);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckValid();

            }
        });

        Button delete = (Button) findViewById(R2.id.deleteCard);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(edit_credit.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_credit.this);
                }
                builder.setTitle("Delete profile")
                        .setMessage("Are you sure you want to delete this profile?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deleteCard();
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
        Button logout = (Button) findViewById(R2.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(edit_credit.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_credit.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(edit_credit.this);
                                Intent intent = new Intent(edit_credit.this, main_login.class);
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



    private void getIncomingIntent()throws IOException{

        if( getIntent().hasExtra("ID")&& getIntent().hasExtra("profile_name")&& getIntent().hasExtra("CreditNum")&& getIntent().hasExtra("ActivateMonitoring")
                && getIntent().hasExtra("SecureCode")&& getIntent().hasExtra("CardOwner")&& getIntent().hasExtra("ExpDate")
                && getIntent().hasExtra("PhoneNum")&& getIntent().hasExtra("Country")&& getIntent().hasExtra("City")
                && getIntent().hasExtra("Street")&& getIntent().hasExtra("ZipCode"))
        {
            ID = getIntent().getStringExtra("ID");
            Nkname = getIntent().getStringExtra("profile_name");
            CardNum = getIntent().getStringExtra("CreditNum");
            ActivateMonitoring = getIntent().getStringExtra("ActivateMonitoring");
            SecCode = getIntent().getStringExtra("SecureCode");
            Owner = getIntent().getStringExtra("CardOwner");
            ExpD = getIntent().getStringExtra("ExpDate");
            PhoneNumber = getIntent().getStringExtra("PhoneNum");
            CountryX = getIntent().getStringExtra("Country");
            CityX = getIntent().getStringExtra("City");
            StreetX = getIntent().getStringExtra("Street");
            ZipCodeX = getIntent().getStringExtra("ZipCode");


            setValue(Nkname,CardNum,ActivateMonitoring, SecCode,Owner,ExpD,PhoneNumber,CountryX,CityX,StreetX,ZipCodeX );
        }
    }

    private void setValue( String Nkname,String CardNumx,String ActivateMonitoring, String SecCodex,String Owner ,String ExpD ,String PhoneNumber ,String Country ,String City,String Street ,String ZipCode) throws IOException{
        if(ActivateMonitoring.equals("1")){

            allowbox.setChecked(true);

        }

        Nickname = findViewById(R2.id.profile_name);
        Nickname.setText(Nkname);

        CardOwner = findViewById(R2.id.CardOwner);
        CardOwner.setText(Owner);

        ExpDate = findViewById(R2.id.expd);
        ExpDate.setText(ExpD);

        PhoneNum = findViewById(R2.id.PhoneNum);
        PhoneNum.setText(PhoneNumber);

        CountryE = findViewById(R2.id.Country);
        CountryE.setText(Country);

        CityE = findViewById(R2.id.City);
        CityE.setText(City);

        CreditNum = findViewById(R2.id.cardnum);
        SecureCode = findViewById(R2.id.secnum);
        StreetE = findViewById(R2.id.Street);
        ZipCodeE = findViewById(R2.id.ZipCode);

        try {
            CardNum=decrypt(CardNumx,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SecCode=decrypt(SecCodex,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            StreetX=decrypt(Street,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ZipCodeX=decrypt(ZipCode,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CreditNum.setText(CardNum);
        SecureCode.setText(SecCode);
        StreetE.setText(StreetX);
        ZipCodeE.setText(ZipCodeX);


    }

    public void CheckValid() {


        UPCardNum = CreditNum.getText().toString();
        UPExpD = ExpDate.getText().toString();
        UPNkname = Nickname.getText().toString();
        UPOwner = CardOwner.getText().toString();
        UPPhoneNumber = PhoneNum.getText().toString();
        UPSecCode = SecureCode.getText().toString();
        UPCityX = CityE.getText().toString();
        UPCountryX = CountryE.getText().toString();
        UPStreetX = StreetE.getText().toString();
        UPZipCodeX = ZipCodeE.getText().toString();

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

            updateCard();

        }
    }


    private void updateCard(){



        try {
            encyCreditNum=encrypt(UPCardNum,TAG);
            encySecureCode=encrypt(UPSecCode,TAG);
            encyStreet=encrypt(UPStreetX,TAG);
            encyZipCode=encrypt(UPZipCodeX,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }



        uname = SaveSharedPreference.getUserName(edit_credit.this).toString();
        String type = "UpdateCard";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID, UPNkname, ActivateMonitoring, encyCreditNum, encySecureCode,
                UPOwner, UPExpD, UPPhoneNumber, UPCountryX,UPCityX, encyStreet, encyZipCode, uname);

        finish();
    }

    private void deleteCard(){

        String type = "deleteCard";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID);
    }

    private String decrypt(String outputString, String password)throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue= Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue=c.doFinal(decodeValue);
        String decryptedValue=new String(decValue);
        return decryptedValue;


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
}
