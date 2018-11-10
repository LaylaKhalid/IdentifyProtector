package com.identifyprotector.identifyprotector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

public class main_login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String sUsername;
    private String sPassword;
    private String NewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.main_login);


        username = (EditText)findViewById(R2.id.editname);
        password = (EditText)findViewById(R2.id.editpass);
        Button imageButton = (Button) findViewById(R2.id.login);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValid();
                OnLogin(v);


            }
        });



    }

    public void CheckValid(){
        sUsername = username.getText().toString();
        sPassword = password.getText().toString();

        if (sUsername.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        }

        if(sPassword.matches("")){
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
        }
        if (sUsername.length()<4) {
            Toast.makeText(this, "You enter a username less than 4 character ", Toast.LENGTH_SHORT).show();
        }
        if (sPassword.length()<8) {
            Toast.makeText(this, "You enter a password less than 8 character ", Toast.LENGTH_SHORT).show();
        }
        NewPassword =  sha256(sPassword);


    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


        public void OnLogin(View view) {
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, sUsername, NewPassword);
            //finish();


        }

    }
