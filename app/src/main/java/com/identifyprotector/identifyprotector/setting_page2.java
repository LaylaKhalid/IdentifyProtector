package com.identifyprotector.identifyprotector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.security.MessageDigest;


public class setting_page2 extends AppCompatActivity {

    private CheckBox check1;
    private String duration;
    private String ActivateAlert;
    private RadioButton d;
    private RadioButton w;
    private RadioButton m;
    private EditText username;
    private EditText password;
    private  String sUsername;
    private String sPassword;
    private String NewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.setting_page2);
        addListnerToCheckBox();


        username = (EditText) findViewById(R2.id.editname);
        password = (EditText) findViewById(R2.id.editpass);




        Button imageButton = (Button) findViewById(R2.id.save);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValid();
                //OnSetting(v);
                if((sPassword.length()>=8)&&(sUsername.length()>=4)){
                    NewPassword =  sha256(sPassword);
                    OnSetting(v);
                }


            }
        });

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
    public void CheckValid(){
         sUsername = username.getText().toString();
         sPassword = password.getText().toString();

        if (sUsername.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        }

        else if (sPassword.matches("")){
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
        }
        else if (sUsername.length()<4) {
            Toast.makeText(this, "Username should not be less than 4 character ", Toast.LENGTH_SHORT).show();
        }
        else if (sPassword.length()<8) {
            Toast.makeText(this, "Password should not be less than 8 character ", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(setting_page2.this, "please follow the instruction", Toast.LENGTH_SHORT).show();

        }


    }

    public void OnSetting(View view) {
        String type = "setting";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, ActivateAlert, duration, sUsername, NewPassword);
        setContentView(R2.layout.activity_login);
    }

    public void addListnerToCheckBox() {

        ActivateAlert = "1";

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

}