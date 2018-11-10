package com.identifyprotector.identifyprotector;

import android.text.TextUtils;
import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class HaveIbeenPwnedAPI {

    public HaveIbeenPwnedAPI(){

    }
    public ArrayList<Breach> query(String account) throws URISyntaxException,IOException {
        String urlApi = "https://haveibeenpwned.com/api/v2/breachedaccount/";
        URL url = new URL(urlApi+account);
        URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
        url = uri.toURL() ;
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
connection.setRequestMethod("GET");
        connection.connect();

if (connection.getResponseCode() == HttpURLConnection.HTTP_OK )
{
    InputStream inputStream  = new BufferedInputStream(connection.getInputStream());

return readJsonStream(connection.getInputStream(), account);
}
else

        return null ;}

private ArrayList<Breach> readJsonStream(InputStream in,String checkAccount) throws IOException {


    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
    try {
        return readResponseArray(reader, checkAccount);
    } finally {
        reader.close();
    }
}

    private ArrayList<Breach> readResponseArray(JsonReader reader, String checkAccount) throws IOException {
        ArrayList<Breach> breaches = new ArrayList<Breach>();

        reader.beginArray();
        while(reader.hasNext()) {
            breaches.add(readBreach(reader, checkAccount));
        }
        reader.endArray();
        return breaches;
    }
    private Breach readBreach(JsonReader reader, String checkAccount) throws IOException {
        String name = null;
        String title = null;
        String description = null;
        String account = checkAccount;

        reader.beginObject();
        while(reader.hasNext()) {
            String tokenName = reader.nextName();
            if(tokenName.equals("Name")) {
                name = reader.nextString();
            } else if(tokenName.equals("Title")) {
                title = reader.nextString();
            } else if(tokenName.equals("Description")) {
                description = reader.nextString();
                String[] invalid = {"? ", " ?", "?","?"};
                String[] correct = {"\"", "\"", "\"", "\""};
                description = TextUtils.replace(description, invalid, correct).toString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Breach(name, title, description, account);
    }
}
