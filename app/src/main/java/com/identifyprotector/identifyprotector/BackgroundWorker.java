package com.identifyprotector.identifyprotector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.library.UniversalPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String>{

        Context context;
        AlertDialog alertDialog;
        private String user_name="";
        private String password="";
        public final static String KEY_USER_NAME = "username";
        public final static String KEY_PASSWORD = "password";
        public final static String IS_LOGIN = "is_login";


    BackgroundWorker(Context ctx) {
        context = ctx;
}
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
      //  String setting_url = "http://10.6.198.245/login.php";
      //  String setting_url = "http://192.168.43.235/setting.php";
        //   String setting_url = "http://10.6.192.55/setting.php";
         String setting_url = "http://192.168.100.14/setting.php";

        if(type.equals("setting")) {
            try {
                String ActAlert = params[1];
                String dura = params[2];
                user_name = params[3];
                password = params[4];

                URL url = new URL(setting_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
             String post_data = URLEncoder.encode("ActivateAlert","UTF-8")+"="+URLEncoder.encode(ActAlert,"UTF-8")+"&"
                     +URLEncoder.encode("duration","UTF-8")+"="+URLEncoder.encode(dura,"UTF-8")+"&"
                     +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                     +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      //  String login_url = "http://192.168.43.235/login.php";
        String login_url = "http://192.168.100.14/login.php";

        if(type.equals("login")) {
            try {
                // String user_name = params[1];
                user_name = params[1];
                password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                //  String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


      //  String credential_url = "http://192.168.43.235/AddLoginCredential.php";
        String credential_url = "http://192.168.100.14/AddLoginCredential.php";
        if(type.equals("credential")) {
        try {
            // String user_name = params[1];
            String Nickname = params[1];
            String appname = params[2];
            String ActivateMonitoring = params[3];
            String userN = params[4];
            String pass = params[5];
            user_name = params[6];
            URL url = new URL(credential_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(Nickname,"UTF-8")+"&"
                    +URLEncoder.encode("appname","UTF-8")+"="+URLEncoder.encode(appname,"UTF-8")+"&"
                    +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(ActivateMonitoring,"UTF-8")+"&"
                    +URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(userN,"UTF-8")+"&"
                    +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&"
                    +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        if(type.equals("deletelog")) {
            try {
                String ID = params[1];

              //  String Delelogin_url = "http://192.168.43.235/deletelog.php?ID="+ID;
                String Delelogin_url = "http://192.168.100.14/deletelog.php?ID="+ID;
                URL url = new URL(Delelogin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //     String credential_url = "http://192.168.43.235/AddLoginCredential.php";
        if(type.equals("updatelog")) {
            try {
                String ID = params[1];
                String upNickname = params[2];
                String upappname = params[3];
                String upActivateMonitoring = params[4];
                String upuserN = params[5];
                String uppass = params[6];
                user_name = params[7];

                String Updatecredential_url = "http://192.168.100.14/updatedlog.php?ID="+ID;
              //  String Updatecredential_url = "http://192.168.43.235/updatedlog.php?ID="+ID;
                URL url = new URL(Updatecredential_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(upNickname,"UTF-8")+"&"
                        +URLEncoder.encode("appname","UTF-8")+"="+URLEncoder.encode(upappname,"UTF-8")+"&"
                        +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(upActivateMonitoring,"UTF-8")+"&"
                        +URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(upuserN,"UTF-8")+"&"
                        +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(uppass,"UTF-8")+"&"
                        +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

      //  String Card_url = "http://192.168.43.235/AddCard.php";
        String Card_url = "http://192.168.100.14/AddCard.php";
        if(type.equals("Card")) {
            try {
                String Nickname = params[1];
                String ActivateMonitoring = params[2];
                String CreditNum = params[3];
                String SecureCode = params[4];
                String CardOwner = params[5];
                String ExpDate = params[6];
                String PhoneNum = params[7];
                String Country = params[8];
                String City = params[9];
                String Street = params[10];
                String ZipCode = params[11];
                user_name = params[12];
                URL url = new URL(Card_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(Nickname,"UTF-8")+"&"
                        +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(ActivateMonitoring,"UTF-8")+"&"
                        +URLEncoder.encode("CreditNum","UTF-8")+"="+URLEncoder.encode(CreditNum,"UTF-8")+"&"
                        +URLEncoder.encode("SecureCode","UTF-8")+"="+URLEncoder.encode(SecureCode,"UTF-8")+"&"
                        +URLEncoder.encode("CardOwner","UTF-8")+"="+URLEncoder.encode(CardOwner,"UTF-8")+"&"
                        +URLEncoder.encode("ExpDate","UTF-8")+"="+URLEncoder.encode(ExpDate,"UTF-8")+"&"
                        +URLEncoder.encode("PhoneNum","UTF-8")+"="+URLEncoder.encode(PhoneNum,"UTF-8")+"&"
                        +URLEncoder.encode("Country","UTF-8")+"="+URLEncoder.encode(Country,"UTF-8")+"&"
                        +URLEncoder.encode("City","UTF-8")+"="+URLEncoder.encode(City,"UTF-8")+"&"
                        +URLEncoder.encode("Street","UTF-8")+"="+URLEncoder.encode(Street,"UTF-8")+"&"
                        +URLEncoder.encode("ZipCode","UTF-8")+"="+URLEncoder.encode(ZipCode,"UTF-8")+"&"
                        +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // String login_url = "http://10.6.198.245/login.php";
        // String Card_url = "http://192.168.100.14/AddCard.php";
        //  String login_url = "http://192.168.100.14/login.php";
        if(type.equals("UpdateCard")) {
            try {
                String ID=params[1];
                String Nickname = params[2];
                String ActivateMonitoring = params[3];
                String CreditNum = params[4];
                String SecureCode = params[5];
                String CardOwner = params[6];
                String ExpDate = params[7];
                String PhoneNum = params[8];
                String Country = params[9];
                String City = params[10];
                String Street = params[11];
                String ZipCode = params[12];
                user_name = params[13];

                String UPCard_url = "http://192.168.100.14/updateCard.php?ID="+ID;
              //  String UPCard_url = "http://192.168.43.235/updateCard.php?ID="+ID;
                URL url = new URL(UPCard_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(Nickname,"UTF-8")+"&"
                        +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(ActivateMonitoring,"UTF-8")+"&"
                        +URLEncoder.encode("CreditNum","UTF-8")+"="+URLEncoder.encode(CreditNum,"UTF-8")+"&"
                        +URLEncoder.encode("SecureCode","UTF-8")+"="+URLEncoder.encode(SecureCode,"UTF-8")+"&"
                        +URLEncoder.encode("CardOwner","UTF-8")+"="+URLEncoder.encode(CardOwner,"UTF-8")+"&"
                        +URLEncoder.encode("ExpDate","UTF-8")+"="+URLEncoder.encode(ExpDate,"UTF-8")+"&"
                        +URLEncoder.encode("PhoneNum","UTF-8")+"="+URLEncoder.encode(PhoneNum,"UTF-8")+"&"
                        +URLEncoder.encode("Country","UTF-8")+"="+URLEncoder.encode(Country,"UTF-8")+"&"
                        +URLEncoder.encode("City","UTF-8")+"="+URLEncoder.encode(City,"UTF-8")+"&"
                        +URLEncoder.encode("Street","UTF-8")+"="+URLEncoder.encode(Street,"UTF-8")+"&"
                        +URLEncoder.encode("ZipCode","UTF-8")+"="+URLEncoder.encode(ZipCode,"UTF-8")+"&"
                        +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(type.equals("deleteCard")) {
            try {
                String ID = params[1];

                String Delelogin_url = "http://192.168.100.14/deleteCard.php?ID="+ID;
               // String Delelogin_url = "http://192.168.43.235/deleteCard.php?ID="+ID;
                URL url = new URL(Delelogin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // String login_url = "http://10.6.198.245/login.php";
       // String Personal_url = "http://192.168.43.235/AddPersonal.php";
        String Personal_url = "http://192.168.100.14/AddPersonal.php";

        //  String login_url = "http://192.168.100.14/login.php";
        if(type.equals("Personal")) {
            try {
                String Nickname = params[1];
                String ActivateMonitoring = params[2];
                String FirstN = params[3];
                String MiddleN = params[4];
                String LastN = params[5];
                String DOBp = params[6];
                String NationalID = params[7];
                String PassportNum = params[8];
                String email = params[9];
                String PhoneNum = params[10];
                String Country = params[11];
                String City = params[12];
                String Street = params[13];
                String ZipCode = params[14];
                user_name = params[15];
                URL url = new URL(Personal_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(Nickname,"UTF-8")+"&"
                        +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(ActivateMonitoring,"UTF-8")+"&"
                        +URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(FirstN,"UTF-8")+"&"
                        +URLEncoder.encode("MiddleMame","UTF-8")+"="+URLEncoder.encode(MiddleN,"UTF-8")+"&"
                        +URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(LastN,"UTF-8")+"&"
                        +URLEncoder.encode("DOB","UTF-8")+"="+URLEncoder.encode(DOBp,"UTF-8")+"&"
                        +URLEncoder.encode("NationalID","UTF-8")+"="+URLEncoder.encode(NationalID,"UTF-8")+"&"
                        +URLEncoder.encode("PassportNumber","UTF-8")+"="+URLEncoder.encode(PassportNum,"UTF-8")+"&"
                        +URLEncoder.encode("mail","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("PhoneNum","UTF-8")+"="+URLEncoder.encode(PhoneNum,"UTF-8")+"&"
                        +URLEncoder.encode("Country","UTF-8")+"="+URLEncoder.encode(Country,"UTF-8")+"&"
                        +URLEncoder.encode("City","UTF-8")+"="+URLEncoder.encode(City,"UTF-8")+"&"
                        +URLEncoder.encode("Street","UTF-8")+"="+URLEncoder.encode(Street,"UTF-8")+"&"
                        +URLEncoder.encode("ZipCode","UTF-8")+"="+URLEncoder.encode(ZipCode,"UTF-8")+"&"
                        +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(type.equals("deletePersonal")) {
            try {
                String ID = params[1];

               // String Delelogin_url = "http://192.168.43.235/deletePersonal.php?ID="+ID;
                String Delelogin_url = "http://192.168.100.14/deletePersonal.php?ID="+ID;
                URL url = new URL(Delelogin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // String UPPersonal_url = "http://192.168.43.235/AddPersonal.php";
        //  String UPPersonal_url = "http://192.168.100.14/login.php";
        if(type.equals("UpdatePersonal")) {
            try {
                String ID =params[1];
                String Nickname = params[2];
                String ActivateMonitoring = params[3];
                String FirstN = params[4];
                String MiddleN = params[5];
                String LastN = params[6];
                String DOBp = params[7];
                String NationalID = params[8];
                String PassportNum = params[9];
                String email = params[10];
                String PhoneNum = params[11];
                String Country = params[12];
                String City = params[13];
                String Street = params[14];
                String ZipCode = params[15];

                String UPPersonal_url = "http://192.168.100.14/updatePersonal.php?ID="+ID;
               // String UPPersonal_url = "http://192.168.43.235/updatePersonal.php?ID="+ID;
                URL url = new URL(UPPersonal_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Nickname","UTF-8")+"="+URLEncoder.encode(Nickname,"UTF-8")+"&"
                        +URLEncoder.encode("ActivateMonitoring","UTF-8")+"="+URLEncoder.encode(ActivateMonitoring,"UTF-8")+"&"
                        +URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(FirstN,"UTF-8")+"&"
                        +URLEncoder.encode("MiddleMame","UTF-8")+"="+URLEncoder.encode(MiddleN,"UTF-8")+"&"
                        +URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(LastN,"UTF-8")+"&"
                        +URLEncoder.encode("DOB","UTF-8")+"="+URLEncoder.encode(DOBp,"UTF-8")+"&"
                        +URLEncoder.encode("NationalID","UTF-8")+"="+URLEncoder.encode(NationalID,"UTF-8")+"&"
                        +URLEncoder.encode("PassportNumber","UTF-8")+"="+URLEncoder.encode(PassportNum,"UTF-8")+"&"
                        +URLEncoder.encode("mail","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("PhoneNum","UTF-8")+"="+URLEncoder.encode(PhoneNum,"UTF-8")+"&"
                        +URLEncoder.encode("Country","UTF-8")+"="+URLEncoder.encode(Country,"UTF-8")+"&"
                        +URLEncoder.encode("City","UTF-8")+"="+URLEncoder.encode(City,"UTF-8")+"&"
                        +URLEncoder.encode("Street","UTF-8")+"="+URLEncoder.encode(Street,"UTF-8")+"&"
                        +URLEncoder.encode("ZipCode","UTF-8")+"="+URLEncoder.encode(ZipCode,"UTF-8")+"&"
                        +URLEncoder.encode("UserID","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
}

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Notification :");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        if(result.contains("login success !!!!! Welcome user"))
        {
            Intent i = new Intent(context,login.class);
            context.startActivity(i);
            SaveSharedPreference newpref = new SaveSharedPreference();
            newpref.setUserName(context, user_name);

        }

        //add Notification
        if(result.contains("Your Login profile is added successfully"))
        {
            Toast.makeText(context, "Your Login profile is added successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,login_interface.class);
            context.startActivity(i);

        }
        if(result.contains("Your credit card profile is added successfully"))
        {
            Toast.makeText(context, "Your credit card profile is added successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,credit_iterface.class);
            context.startActivity(i);
        }
        if(result.contains("Your personal profile is added successfully"))
        {
            Toast.makeText(context, "Your personal profile is added successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,personal_iterface.class);
            context.startActivity(i);
        }
        //update Notification
        if(result.contains("Your Login profile is updated successfully"))
        {
            Toast.makeText(context, "Your Login profile is updated successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,login_interface.class);
            context.startActivity(i);

        }
        if(result.contains("Your Card profile is updated successfully"))
        {
            Toast.makeText(context, "Your Card profile is updated successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,credit_iterface.class);
            context.startActivity(i);
        }
        if(result.contains("Your personal profile is updated successfully"))
        {
            Toast.makeText(context, "Your personal profile is updated successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,personal_iterface.class);
            context.startActivity(i);
        }
        //delete Notification
        if(result.contains("Your login credential profile is deleted successfully"))
        {
            Toast.makeText(context, "Your login credential profile is deleted successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,login_interface.class);
            context.startActivity(i);

        }
        if(result.contains("Your Card profile is deleted successfully"))
        {
            Toast.makeText(context, "Your Card profile is deleted successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,credit_iterface.class);
            context.startActivity(i);
        }
        if(result.contains("Your personal profile is deleted successfully"))
        {
            Toast.makeText(context, "Your personal profile is deleted successfully ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,personal_iterface.class);
            context.startActivity(i);
        }





    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}



