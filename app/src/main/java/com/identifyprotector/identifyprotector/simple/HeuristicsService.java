/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.identifyprotector.identifyprotector.simple;

import android.app.assist.AssistStructure;
import android.app.assist.AssistStructure.ViewNode;
import android.content.Context;
import android.content.IntentSender;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveInfo;
import android.service.autofill.SaveRequest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.identifyprotector.identifyprotector.MyAutofillService;
import com.identifyprotector.identifyprotector.settings.MyPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import static com.identifyprotector.identifyprotector.simple.BasicService.getLatestAssistStructure;
import static com.identifyprotector.identifyprotector.simple.BasicService.newDatasetPresentation;

/**
 * A basic service that uses some rudimentary heuristics to identify fields that are not explicitly
 * marked with autofill hints.
 *
 * <p>The goal of this class is to provide a simple autofill service implementation that is easy
 * to understand and extend, but it should <strong>not</strong> be used as-is on real apps because
 * it lacks fundamental security requirements such as data partitioning and package verification
 * &mdashthese requirements are fullfilled by {@link MyAutofillService}.
 */
public class HeuristicsService extends AutofillService {

    private static final String TAG = "HeuristicsService";
    //private static JSONObject jsonResult=null;
    //private static JSONArray logincredentials=null;
    //private static JSONObject logincredential=null;
    private static int size;
    private static int size2;
    private static int size3=size+size2;
    public static creditcard[]credit;
    public static logincredentials []login;
    private boolean mAuthenticateResponses;
    private boolean mAuthenticateDatasets;
    private int mNumberDatasets = 7;
    private static final String URL_PRODUCTS = "http://192.168.1.10/identityprotector/logincredentials.php";
    private static final String URL_cridt = "http://192.168.1.10/identityprotector/creditcard.php";

    //a list to store all the products
    //List<logincredentials> loginList;

    @Override
    public void onConnected() {
        //new Connection().execute();
        super.onConnected();

        // TODO(b/114236837): use its own preferences?
        MyPreferences pref = MyPreferences.getInstance(getApplicationContext());
        mAuthenticateResponses = pref.isResponseAuth();
        mAuthenticateDatasets = pref.isDatasetAuth();
        // TODO(b/114236837): get number dataset from preferences

        Log.d(TAG, "onConnected(): numberDatasets=" + mNumberDatasets
                + ", authResponses=" + mAuthenticateResponses
                + ", authDatasets=" + mAuthenticateDatasets);
        //new Connection().execute();
       // loginList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
        loadCridt();
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            size=array.length();
                            login = new logincredentials[size];
                            //traversing through all the object
                            for (int i = 0; i < size; i++) {

                                //getting product object from json array
                                JSONObject logincredentials = array.getJSONObject(i);

                                //adding the product to product list
                                login[i] = new logincredentials(logincredentials.getInt("ID"),
                                        logincredentials.getString("Nickname"),
                                        logincredentials.getString("appname"),
                                        logincredentials.getString("ActivateMonitoring"),
                                        logincredentials.getString("user"),
                                        logincredentials.getString("pass"),
                                        logincredentials.getString("UserID")


                                );
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadCridt() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_cridt,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            size2=array.length();
                            credit= new creditcard[size2];
                            //traversing through all the object
                            for (int i = 0; i < size2; i++) {

                                //getting product object from json array
                                JSONObject creditcard = array.getJSONObject(i);

                                //adding the product to product list
                                credit[i] = new creditcard(creditcard.getInt("ID"),
                                        creditcard.getString("Nickname"),
                                        creditcard.getString("ActivateMonitoring"),
                                        creditcard.getString("CreditNum"),
                                        creditcard.getString("SecureCode"),
                                        creditcard.getString("CardOwner"),
                                        creditcard.getString("ExpDate"),
                                        creditcard.getString("PhoneNum"),
                                        creditcard.getString("Country"),
                                        creditcard.getString("City"),
                                        creditcard.getString("Street"),
                                        creditcard.getString("ZipCode"),
                                        creditcard.getString("UserID")




                                        );
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    protected class info{

        protected String nick;
        protected String name;
        protected String pass;
        public info(String nick, String name, String pass){

            this.name=name;
            this.nick=nick;
            this.pass=pass;
        }
        public void setNick(String nick){
            nick=nick;
        }
        public void setName(String name){
            name=name;
        }
        public void setPass(String pass){
            pass=pass;
        }
         public String getNick(){
            return nick;
         }
        public String getName(){
            return name;
        }
        public String getPass(){
            return pass;
        }

    }
/*
    class Connection extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String host = "http://10.5.199.91/identityprotector/logincredentials.php";
            try {
                HttpClient clint = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response= clint.execute(request);
                BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer=new StringBuffer("");
                String line="";
                while ((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result= stringBuffer.toString();
            } catch (Exception e) {
                return new String("There exception: "+ e.getMessage());
            }


            return result;
        }

        protected void onPostExecute(String result){



            try {
                jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");
                if (success == 1){
                    logincredentials= jsonResult.getJSONArray("logincredentials");
                    size=logincredentials.length();
                    infos=new  info[size];
                    for(int i=0; i<size; i++){
                        logincredential = logincredentials.getJSONObject(i);
                        int ID = logincredential.getInt("ID");
                        String Nickname= logincredential.getString("Nickname");
                        String Type= logincredential.getString("Type");
                        int ActivateMonitoring = logincredential.getInt("ActivateMonitoring");
                        String ApplicationName= logincredential.getString("ApplicationName");
                        String UserName= logincredential.getString("UserName");
                        String Password = logincredential.getString("Password");
                        int UserID = logincredential.getInt("UserID");
                        infos[i]=new info(Nickname, UserName, Password);


                    }
                }

                else {
                    Toast.makeText(getApplicationContext(), "no profiles yet", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }}*/

    @Override
    public void onFillRequest(FillRequest request, CancellationSignal cancellationSignal,
                              FillCallback callback) {
        Log.d(TAG, "onFillRequest()");

        // Find autofillable fields
        AssistStructure structure = getLatestAssistStructure(request);
        ArrayMap<String, AutofillId> fields = getAutofillableFields(structure);
        Log.d(TAG, "autofillable fields:" + fields);

        if (fields.isEmpty()) {
            toast("No autofill hints found");
            callback.onSuccess(null);
            return;
        }

        // Create response...
        FillResponse response;
        if (mAuthenticateResponses) {
            int size = fields.size();
            String[] hints = new String[size];
            AutofillId[] ids = new AutofillId[size];
            for (int i = 0; i < size; i++) {
                hints[i] = fields.keyAt(i);
                ids[i] = fields.valueAt(i);
            }

            IntentSender authentication = SimpleAuthActivity.newIntentSenderForResponse(this, hints,
                    ids, mAuthenticateDatasets);
            RemoteViews presentation = newDatasetPresentation(getPackageName(),
                    "Tap to auth response");

            response = new FillResponse.Builder()
                    .setAuthentication(ids, authentication, presentation).build();
        } else {
            response = createResponse(this, fields, mNumberDatasets,mAuthenticateDatasets);
        }

        // ... and return it
        callback.onSuccess(response);
    }

    @Override
    public void onSaveRequest(SaveRequest request, SaveCallback callback) {
        Log.d(TAG, "onSaveRequest()");
        toast("Save not supported");
        callback.onSuccess();
    }

    /**
     * Parses the {@link AssistStructure} representing the activity being autofilled, and returns a
     * map of autofillable fields (represented by their autofill ids) mapped by the hint associate
     * with them.
     *
     * <p>An autofillable field is a {@link ViewNode} whose {@link #getHint(ViewNode)} metho
     */
    @NonNull
    private ArrayMap<String, AutofillId> getAutofillableFields(@NonNull AssistStructure structure) {
        ArrayMap<String, AutofillId> fields = new ArrayMap<>();
        int nodes = structure.getWindowNodeCount();
        for (int i = 0; i < nodes; i++) {
            ViewNode node = structure.getWindowNodeAt(i).getRootViewNode();
            addAutofillableFields(fields, node);
        }
        return fields;
    }

    /**
     * Adds any autofillable view from the {@link ViewNode} and its descendants to the map.
     */
    private void addAutofillableFields(@NonNull Map<String, AutofillId> fields,
                                       @NonNull ViewNode node) {
        String hint = getHint(node);
        if (hint != null) {
            AutofillId id = node.getAutofillId();
            if (!fields.containsKey(hint)) {
                Log.v(TAG, "Setting hint '" + hint + "' on " + id);
                fields.put(hint, id);
            } else {
                Log.v(TAG, "Ignoring hint '" + hint + "' on " + id
                        + " because it was already set");
            }
        }
        int childrenSize = node.getChildCount();
        for (int i = 0; i < childrenSize; i++) {
            addAutofillableFields(fields, node.getChildAt(i));
        }
    }

    @Nullable
    protected String getHint(@NonNull ViewNode node) {

        // First try the explicit autofill hints...

        String[] hints = node.getAutofillHints();
        if (hints != null) {
            // We're simple, we only care about the first hint
            return hints[0].toLowerCase();
        }

        // Then try some rudimentary heuristics based on other node properties

        String viewHint = node.getHint();
        String hint = inferHint(node, viewHint);
        if (hint != null) {
            Log.d(TAG, "Found hint using view hint(" + viewHint + "): " + hint);
            return hint;
        } else if (!TextUtils.isEmpty(viewHint)) {
            Log.v(TAG, "No hint using view hint: " + viewHint);
        }

        String resourceId = node.getIdEntry();
        hint = inferHint(node, resourceId);
        if (hint != null) {
            Log.d(TAG, "Found hint using resourceId(" + resourceId + "): " + hint);
            return hint;
        } else if (!TextUtils.isEmpty(resourceId)) {
            Log.v(TAG, "No hint using resourceId: " + resourceId);
        }

        CharSequence text = node.getText();
        CharSequence className = node.getClassName();
        if (text != null ) {
            hint = inferHint(node, text.toString());
            if (hint != null) {
                // NODE: text should not be logged, as it could contain PII
                Log.d(TAG, "Found hint using text(" + text + "): " + hint);
                return hint;
            }
        } else if (!TextUtils.isEmpty(text)) {
            // NODE: text should not be logged, as it could contain PII
            Log.v(TAG, "No hint using text: " + text + " and class " + className);
        }
        return null;
    }

    /**
     * Uses heuristics to infer an autofill hint from a {@code string}.
     *
     * @return standard autofill hint, or {@code null} when it could not be inferred.
     */
    @Nullable
    protected String inferHint(ViewNode node, @Nullable String string) {
        if (string == null) return null;

        string = string.toLowerCase();
        if (string.contains("label")) {
            Log.v(TAG, "Ignoring 'label' hint: " + string);
            return null;
        }
        if (string.contains("password")) return View.AUTOFILL_HINT_PASSWORD;
        if (string.contains("postalAddress")) return View.AUTOFILL_HINT_POSTAL_ADDRESS;
        if (string.contains("postalCode")) return View.AUTOFILL_HINT_POSTAL_CODE;
        if (string.contains("creditCardSecurityCode")) return View.AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE;
        if (string.contains("creditCardNumber")) return View.AUTOFILL_HINT_CREDIT_CARD_NUMBER;
        if (string.contains("creditCardExpirationDate")) return View.AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE;
        if (string.contains("username") || (string.contains("login") && string.contains("id"))) return View.AUTOFILL_HINT_USERNAME;
        if (string.contains("email")) return View.AUTOFILL_HINT_EMAIL_ADDRESS;
        if (string.contains("name")) return View.AUTOFILL_HINT_NAME;
        if (string.contains("phone")) return View.AUTOFILL_HINT_PHONE;

        // When everything else fails, return the full string - this is helpful to help app
        // developers visualize when autofill is triggered when it shouldn't (for example, in a
        // chat conversation window), so they can mark the root view of such activities with
        // android:importantForAutofill=noExcludeDescendants
        if (node.isEnabled() && node.getAutofillType() != View.AUTOFILL_TYPE_NONE) {
            Log.v(TAG, "Falling back to " + string);
            return string;
        }
        return null;
    }

    static FillResponse createResponse(@NonNull Context context,
                                       @NonNull ArrayMap<String, AutofillId> fields, int numDatasets,
                                       boolean authenticateDatasets) {
        String packageName = context.getPackageName();
        FillResponse.Builder response = new FillResponse.Builder();
        // 1.Add the dynamic datasets
        for (int i = 0; i <2; i++) {
            Dataset unlockedDataset = newUnlockedDataset(fields, packageName, i);
            if (authenticateDatasets) {
                Dataset.Builder lockedDataset = new Dataset.Builder();
                for (Entry<String, AutofillId> field : fields.entrySet()) {
                    String hint = "data";
                    /*
                    String hint2 = field.getKey();

                        if ( hint2.contains("username") == true || hint2.contains("password") == true){
                            hint= login[i].getNickname();  }
                            else{
                            hint=credit[i].getNickname();


                    }*/
                    AutofillId id = field.getValue();
                    String value = i + "-" + hint;
                    IntentSender authentication =
                            SimpleAuthActivity.newIntentSenderForDataset(context, unlockedDataset);
                    RemoteViews presentation = newDatasetPresentation(packageName,
                              value);
                    lockedDataset.setValue(id, null, presentation)
                            .setAuthentication(authentication);
                }
                response.addDataset(lockedDataset.build());
            } else {
                response.addDataset(unlockedDataset);
            }


        }


        // 2.Add save info
        Collection<AutofillId> ids = fields.values();
        AutofillId[] requiredIds = new AutofillId[ids.size()];
        ids.toArray(requiredIds);
        response.setSaveInfo(
                // We're simple, so we're generic
                new SaveInfo.Builder(SaveInfo.SAVE_DATA_TYPE_GENERIC, requiredIds).build());

        // 3.Profit!
        return response.build();
    }

    static Dataset newUnlockedDataset(@NonNull Map<String, AutofillId> fields,
                                      @NonNull String packageName, int i) {



        Dataset.Builder dataset = new Dataset.Builder();
        for (Entry<String, AutofillId> field : fields.entrySet()) {
            String hint = field.getKey();
            String hintPA="1073741829";
            String hintPA2="1073741828";
            String hintCN="1073741824";
            String hintSC="1073741825";
            String hintSC2="107";
            String hintCN2="1073741825";
            String hintCard="1073741824";

            String hintCE="1073741827";
            String hintCE2="10737";

            String hintpc="1073741830";


            AutofillId id = field.getValue();
            String value = i + "-" + hint;
            // We're simple - our dataset values are hardcoded as "N-hint" (for example,
            // "1-username", "2-username") and they're displayed as such, except if they're a
            // password

                String displayValue = null;
                AutofillValue autofillValue = null;

//////////////////////////////////////////////logincredentials//////////////////////////////////////////////
         if(i==0){
                if( hint.contains("password") == true){
                    displayValue=  "password for #" + i ;
                    autofillValue= AutofillValue.forText("112233");}

                else if ( hint.contains("username") == true ){
                    displayValue= "snapchat";
                    autofillValue= AutofillValue.forText("layla");}
          /*
           if(i==0){
                if( hint.contains("password") == true){
                    displayValue=  "password for #" + i ;
                    autofillValue= AutofillValue.forText(login[i].getPassword());}

                else if ( hint.contains("username") == true){
                    displayValue= login[i].getNickname();
                    autofillValue= AutofillValue.forText(login[i].getUserName());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741829")== true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(" Olaya  "+ "Makkah  "+"Saudi Arabia");}
                else if ( id.toString().equals(hintCard) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCardOwner());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getZipCode());}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals(hintCN2) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCreditNum());}


                else if ((hint.contains("password") == false && hint.contains("username") == false &&  id.toString().equals("1073741827") == true )){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getSecureCode());}


                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getExpDate());}

                else if ( hint.contains("phone") == true){
                    displayValue="Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getPhoneNum());}
            }

         /*   if(i==2){
                if( hint.contains("password") == true){
                    displayValue=  "password for #" + i ;
                    autofillValue= AutofillValue.forText(login[i].getPassword());}

                else if ( hint.contains("username") == true){
                    displayValue= login[i].getNickname();
                    autofillValue= AutofillValue.forText(login[i].getUserName());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741829")== true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(" Olaya  "+ "Makkah  "+"Saudi Arabia");}
                else if ( id.toString().equals(hintCard) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCardOwner());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getZipCode());}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals(hintCN2) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCreditNum());}


                else if ((hint.contains("password") == false && hint.contains("username") == false &&  id.toString().equals("1073741827") == true )){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getSecureCode());}


                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getExpDate());}

                else if ( hint.contains("phone") == true){
                    displayValue="Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getPhoneNum());}
            }
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////personalinformation/////////////////////////////////////////

                else if ( hint.contains("phone") == true){
                    displayValue="personalinformation";
                    autofillValue= AutofillValue.forText("0502514879");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741824") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("layla khalid");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741825") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("layla@gmail.com");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true ){
                    displayValue= "personalinformation";
                    autofillValue= AutofillValue.forText("0502514879");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741827") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText(" Olaya  "+ "Makkah  "+"Saudi Arabia");}



              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741828") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("25/1/1997");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("Makkah");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741832") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("Saudi Arabia");}
                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741833") == true ){
                    displayValue= "personalinformation";
                    autofillValue= AutofillValue.forText("5555");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741834") == true ){
                    displayValue= "personalinformation";
                    autofillValue= AutofillValue.forText("56215879");}

              else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741835") == true ){
                  displayValue= "personalinformation";
                  autofillValue= AutofillValue.forText("623154");}}
////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////creditcard///////////////////////////////////////////////
if(i==1){
    if( hint.contains("password") == true){
        displayValue=  "password for #" + i ;
        autofillValue= AutofillValue.forText("258258258258");}

    else if ( hint.contains("username") == true ){
        displayValue= "gmail";
        autofillValue= AutofillValue.forText("Reem@gmail.com");}

              else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741829")== true){
                    displayValue= "Al Rajhi Bank";
                   autofillValue= AutofillValue.forText(" Olaya  "+ "Makkah  "+"Saudi Arabia");}
              else if ( id.toString().equals(hintCard) == true ){
                  displayValue= "Al Rajhi Bank";
                  autofillValue= AutofillValue.forText("layla");}

               else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                    displayValue= "Al Rajhi Bank";
                  autofillValue= AutofillValue.forText("6267");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals(hintCN2) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText("6524875216");}


                else if ((hint.contains("password") == false && hint.contains("username") == false &&  id.toString().equals("1073741827") == true )){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText("5555");}


                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText("2018");}

              else if ( hint.contains("phone") == true){
                  displayValue="Al Rajhi Bank";
                  autofillValue= AutofillValue.forText("05025265487");}}


/*

            if(i==1){
                if( hint.contains("password") == true){
                    displayValue=  "password for #" + i ;
                    autofillValue= AutofillValue.forText(login[i].getPassword());}

                else if ( hint.contains("username") == true || hint.contains("phone")){
                    displayValue= login[i].getNickname();
                    autofillValue= AutofillValue.forText(login[i].getUserName());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741829")== true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(" Olaya  "+ "Makkah  "+"Saudi Arabia");}
                else if ( id.toString().equals(hintCard) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCardOwner());}

                else if (  hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getZipCode());}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals(hintCN2) == true ){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getCreditNum());}


                else if ((hint.contains("password") == false && hint.contains("username") == false &&  id.toString().equals("1073741827") == true )){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getSecureCode());}


                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true){
                    displayValue= "Al Rajhi Bank";
                    autofillValue= AutofillValue.forText(credit[i].getExpDate());}

                else if ( hint.contains("phone") == true){
                    displayValue="Al Rajhi Bank";
                  autofillValue= AutofillValue.forText(credit[i].getPhoneNum());}}
/*
            if(i==2){
                if( hint.contains("password") == true){
                    displayValue=  "password for #" + i ;
                    autofillValue= AutofillValue.forText("5566777777");}

                else if ( hint.contains("username") == true){
                    displayValue= "twitter";
                    autofillValue= AutofillValue.forText("Alaa");}*/
////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////personalinformation/////////////////////////////////////////
/*
                else if ( hint.contains("phone") == true){
                    displayValue="layla's information";
                    autofillValue= AutofillValue.forText("0568484848");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741825") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("Sara");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741826") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("Ahmed");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741827") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("Fahed");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741828") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("3636");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741829") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("Saudi Arabia , Dammam , Othman bin Affan ");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741830") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("4141");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741831") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("2001");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741832") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("564654");}

                else if ( hint.contains("password") == false && hint.contains("username") == false && id.toString().equals("1073741833") == true ){
                    displayValue= "layla's information";
                    autofillValue= AutofillValue.forText("lo0oly@hotmail.com");}}*/

                RemoteViews presentation = newDatasetPresentation(packageName, displayValue);
                dataset.setValue(id, autofillValue, presentation);

        }

        return dataset.build();
    }

    /**
     * Displays a toast with the given message.
     */
    private void toast(@NonNull CharSequence message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
