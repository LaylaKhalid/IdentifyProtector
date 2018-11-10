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

public class edit_personal extends AppCompatActivity {
    private EditText Nickname, FirstName, MiddleName, LastName,DOB,NationalID,PassportNumber,mail,PhoneNum,Country,	City,Street,ZipCode;
    private String ID, Nkname,  MiddleN, LastN, DOBp, NationalIDp, PassportNum, email, Phone, Countryp, Cityp, Streetp, ZipCodep;
    private String UPID, UPNkname, UPFirstN, UPMiddleN, UPLastN, UPDOBp, UPNationalIDp, UPPassportNum, UPemail, UPPhone, UPCountryp, UPCityp, UPStreetp, UPZipCodep;
    private String encyNationalID, encyPassportNum,encyemail,encyPhone,encyStreet,encyZipCode;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    String TAG = "AddPersonal";
    String AES="AES";
    private String FirstN="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.edit_personal_information_interface);
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


        Button update = (Button) findViewById(R2.id.updatePersonal);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValid();
                //updatePersonal();
               // finish();

            }
        });

        Button delete = (Button) findViewById(R2.id.deletePersonal);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(edit_personal.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_personal.this);
                }
                builder.setTitle("Delete profile")
                        .setMessage("Are you sure you want to delete this profile?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deletePersonal();
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
                    builder = new AlertDialog.Builder(edit_personal.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_personal.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(edit_personal.this);
                                Intent intent = new Intent(edit_personal.this, main_login.class);
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

        if( getIntent().hasExtra("ID")&& getIntent().hasExtra("profile_name")&& getIntent().hasExtra("FirstName")&& getIntent().hasExtra("ActivateMonitoring")
                && getIntent().hasExtra("MiddleMame")&& getIntent().hasExtra("LastName")&& getIntent().hasExtra("DOB")
                && getIntent().hasExtra("NationalID")&& getIntent().hasExtra("PassportNumber")&& getIntent().hasExtra("mail")
                && getIntent().hasExtra("PhoneNum")&& getIntent().hasExtra("Country")&& getIntent().hasExtra("City")
                && getIntent().hasExtra("Street")&& getIntent().hasExtra("ZipCode"))
        {
            ID = getIntent().getStringExtra("ID");
            Nkname = getIntent().getStringExtra("profile_name");
            FirstN = getIntent().getStringExtra("FirstName");
            MiddleN = getIntent().getStringExtra("MiddleMame");
            LastN = getIntent().getStringExtra("LastName");
            ActivateMonitoring = getIntent().getStringExtra("ActivateMonitoring");
            DOBp = getIntent().getStringExtra("DOB");
            NationalIDp = getIntent().getStringExtra("NationalID");
            PassportNum = getIntent().getStringExtra("PassportNumber");
            email = getIntent().getStringExtra("mail");
            Phone = getIntent().getStringExtra("PhoneNum");
            Countryp = getIntent().getStringExtra("Country");
            Cityp = getIntent().getStringExtra("City");
            Streetp = getIntent().getStringExtra("Street");
            ZipCodep = getIntent().getStringExtra("ZipCode");


            setValue(Nkname,FirstN,MiddleN, LastN, ActivateMonitoring, DOBp,NationalIDp,PassportNum,email,Phone,Countryp,Cityp,Streetp,ZipCodep );
        }
    }

    private void setValue( String Nkname,String FirstN1,String MiddleN, String LastN, String ActivateMonitoring, String DOBp,String National ,String Passport ,String emailP, String PhoneNumber ,String Country1 ,String City1,String Street1 ,String ZipCode1) throws IOException{
        if(ActivateMonitoring.equals("1")){

            allowbox.setChecked(true);

        }

        Nickname = findViewById(R2.id.profile_name);
        Nickname.setText(Nkname);

        FirstName = findViewById(R2.id.FirstN);
        FirstName.setText(FirstN1);

        MiddleName = findViewById(R2.id.MiddleN);
        MiddleName.setText(MiddleN);

        LastName = findViewById(R2.id.LastN);
        LastName.setText(LastN);

        DOB = findViewById(R2.id.DOB);
        DOB.setText(DOBp);

        Country = findViewById(R2.id.Country);
        Country.setText(Country1);

        City = findViewById(R2.id.City);
        City.setText(City1);


        NationalID = findViewById(R2.id.NationalID);
        PassportNumber = findViewById(R2.id.PassportNum);
        mail = findViewById(R2.id.email);
        PhoneNum = findViewById(R2.id.PhoneNum);
        Street = findViewById(R2.id.Street);
        ZipCode = findViewById(R2.id.ZipCode);



        try {
            NationalIDp=decrypt(National,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PassportNum=decrypt(Passport,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            email=decrypt(emailP,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Phone=decrypt(PhoneNumber,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Streetp=decrypt(Street1,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ZipCodep=decrypt(ZipCode1,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NationalID.setText(NationalIDp);
        PassportNumber.setText(PassportNum);
        mail.setText(email);
        PhoneNum.setText(Phone);
        Street.setText(Streetp);
        ZipCode.setText(ZipCodep);

    }

    public void CheckValid() {


        UPNkname=Nickname.getText().toString();
        UPFirstN=FirstName.getText().toString();
        UPMiddleN=MiddleName.getText().toString();
        UPLastN=LastName.getText().toString();
        UPDOBp=DOB.getText().toString();
        UPNationalIDp=NationalID.getText().toString();
        UPemail=mail.getText().toString();
        UPPassportNum=PassportNumber.getText().toString();
        UPPhone=PhoneNum.getText().toString();
        UPCountryp=Country.getText().toString();
        UPCityp=City.getText().toString();
        UPStreetp=Street.getText().toString();
        UPZipCodep=ZipCode.getText().toString();

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

            updatePersonal();
        }
    }


    private void updatePersonal(){

/*
        UPNkname=Nickname.getText().toString();
        UPFirstN=FirstName.getText().toString();
        UPMiddleN=MiddleName.getText().toString();
        UPLastN=LastName.getText().toString();
        UPDOBp=DOB.getText().toString();
        UPNationalIDp=NationalID.getText().toString();
        UPemail=mail.getText().toString();
        UPPassportNum=PassportNumber.getText().toString();
        UPPhone=PhoneNum.getText().toString();
        UPCountryp=Country.getText().toString();
        UPCityp=City.getText().toString();
        UPStreetp=Street.getText().toString();
        UPZipCodep=ZipCode.getText().toString();*/

        try {
            encyNationalID=encrypt(UPNationalIDp,TAG);
            encyPassportNum=encrypt(UPPassportNum,TAG);
            encyemail=encrypt(UPemail,TAG);
            encyPhone=encrypt(UPPhone,TAG);
            encyStreet=encrypt(UPStreetp,TAG);
            encyZipCode=encrypt(UPZipCodep,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }



        uname = SaveSharedPreference.getUserName(edit_personal.this).toString();
        String type = "UpdatePersonal";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID, UPNkname, ActivateMonitoring, UPFirstN, UPMiddleN,
                UPLastN, UPDOBp, encyNationalID,encyPassportNum,encyemail,encyPhone, UPCountryp,UPCityp, encyStreet, encyZipCode);

        finish();
    }



    private void deletePersonal(){

        String type = "deletePersonal";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID);
    }

//enc_dec
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
