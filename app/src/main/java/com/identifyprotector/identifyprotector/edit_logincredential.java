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

public class edit_logincredential extends AppCompatActivity {

    private EditText Profilename, Uname, passU, appname;
    private String filename="";
    private String user_name="";
    private String pass="";
    private String uname ="";
    private String upfilename="";
    private String upuser_name="";
    private String uppass="";
    private String upAppname, ID, Appname;
    private String encyUser="";
    private String encyPass="";
    String TAG = "AddLogin";
    private CheckBox allowbox;
    private String ActivateMonitoring="";
    String AES="AES";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.edit_login_credentials_interface);
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


        Button update = (Button) findViewById(R2.id.updatelogin);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckValid();

            }
        });

        Button delete = (Button) findViewById(R2.id.deletelogin);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(edit_logincredential.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_logincredential.this);
                }
                builder.setTitle("Delete profile")
                        .setMessage("Are you sure you want to delete this profile?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                deletelog();
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
                    builder = new AlertDialog.Builder(edit_logincredential.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(edit_logincredential.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(edit_logincredential.this);
                                Intent intent = new Intent(edit_logincredential.this, main_login.class);
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
        upfilename = Profilename.getText().toString();
        upuser_name = Uname.getText().toString();
        uppass = passU.getText().toString();
        upAppname = appname.getText().toString();

        if (upfilename.matches("")) {
            Toast.makeText(this, "You did not enter a Profile name", Toast.LENGTH_SHORT).show();
        } else if (upuser_name.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        } else if (uppass.matches("")) {
            Toast.makeText(this, "You did not enter a  password", Toast.LENGTH_SHORT).show();
        } else if (upAppname.matches("")) {
            Toast.makeText(this, "You did not enter a  App name", Toast.LENGTH_SHORT).show();
        } else {

            updatelog();
        }
    }

    private void getIncomingIntent()throws IOException{

        if( getIntent().hasExtra("ID")&& getIntent().hasExtra("profile_name")&& getIntent().hasExtra("appname")&& getIntent().hasExtra("ActivateMonitoring")&& getIntent().hasExtra("username")&& getIntent().hasExtra("password")){

            ID = getIntent().getStringExtra("ID");
            filename = getIntent().getStringExtra("profile_name");
            Appname = getIntent().getStringExtra("appname");
            ActivateMonitoring = getIntent().getStringExtra("ActivateMonitoring");
            user_name = getIntent().getStringExtra("username");
            pass = getIntent().getStringExtra("password");


            setValue(filename,Appname,ActivateMonitoring, user_name,pass );
        }
    }

    private void setValue( String profile_name,String Appname,String ActivateMonitoring, String username,String password) throws IOException{
        if(ActivateMonitoring.equals("1")){

         allowbox.setChecked(true);

         }
        Profilename = findViewById(R2.id.profile_name);
        Profilename.setText(profile_name);
        appname = findViewById(R2.id.appname);
        appname.setText(Appname);

        Uname = findViewById(R2.id.Uname);
        passU = findViewById(R2.id.Upass);

        try {
            user_name=decrypt(username,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pass=decrypt(password,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uname.setText(user_name);
        passU.setText(pass);


    }


    private void updatelog(){

        try {
            encyUser=encrypt(upuser_name,TAG);
            encyPass=encrypt(uppass,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        uname = SaveSharedPreference.getUserName(edit_logincredential.this).toString();
        String type = "updatelog";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID,upfilename,upAppname, ActivateMonitoring,encyUser,encyPass,uname );
        finish();
    }

    private void deletelog(){

        String type = "deletelog";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,ID);
    }

    private String decrypt(String outputString, String password)throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue=Base64.decode(outputString,Base64.DEFAULT);
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
