package courier.kishore.com.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;



import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import courier.kishore.com.test.Declaration.ConnectionDetector;
import dmax.dialog.SpotsDialog;


public class Registration extends Fragment {
    View rootview;
    private static final String TAG = "REGISTRATION";

    private EditText email,vmail;
    private EditText name;
    private EditText pass;
    private EditText repass;
    private EditText phone;
    private EditText dob;
    private EditText city;
    private RadioButton femaleBtn ;
    private RadioButton maleBtn ;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    String verifymail;
    // Connection detector class
    ConnectionDetector cd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.registration, container, false);
        cd = new ConnectionDetector(rootview.getContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {

        } else {

            showAlertDialog(getActivity(), "No Internet Connection",
                    "You don't have internet connection.", false);
        }

        Button btnSearch = (Button) rootview.findViewById(R.id.button2);
        btnSearch.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {


                int flag;

                email = (EditText) rootview.findViewById(R.id.username);
                name = (EditText) rootview.findViewById(R.id.name);
                pass = (EditText) rootview.findViewById(R.id.password);
                repass = (EditText) rootview.findViewById(R.id.repass);
                phone = (EditText) rootview.findViewById(R.id.phone);
                dob = (EditText) rootview.findViewById(R.id.dob);
                femaleBtn = (RadioButton) rootview.findViewById(R.id.gender_female);
                maleBtn = (RadioButton) rootview.findViewById(R.id.gender_male);
                city = (EditText) rootview.findViewById(R.id.city);

                if(isValidEmail(email.getText().toString().trim())){
                    flag = 1;
                }else{
                   email.setError("Invalid");
                   email.requestFocus();
                   flag = 0;
                }

                if(name.getText().toString().trim().equals("")){
                    name.setError("Cannot be blank");
                    name.requestFocus();
                    flag = 0;
                }

                if(dob.getText().toString().trim().equals("")){
                    dob.setError("Invalid Date");
                    dob.requestFocus();
                    flag = 0;
                }

                if(city.getText().toString().trim().equals("")){
                    city.setError("Field cannot be blank");
                    city.requestFocus();
                    flag = 0;
                }

                if(validCellPhone(phone.getText().toString().trim())){
                    flag = 2;
                }else{
                    phone.setError("Invalid");
                    phone.requestFocus();
                    flag = 0;
                }

                if(pass.getText().toString().trim().equals(repass.getText().toString().trim())){
                    flag = 3;
                }else{
                    repass.setError("They should match");
                    repass.requestFocus();
                    flag = 0;
                }

                if(flag!=0){
                    Verification vobj = new Verification();
                    vobj.execute();
                }

            }
        });

        return rootview;
    }


    class Verification extends AsyncTask<String, Void, String> {

        private Exception exception;
        AlertDialog dialog = new SpotsDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String[] args) {
            vmail = (EditText) rootview.findViewById(R.id.username);

            verifymail = vmail.getText().toString().trim();

            Log.d(TAG, "execute1 " + verifymail);
            DefaultHttpClient client = new DefaultHttpClient();




                String url = "http://freecourierservice.appspot.com/rest/user/verification/" + verifymail;
                HttpGet request = new HttpGet(url);
                String responseStr = "";
                try {

                    HttpResponse response = client.execute(request);
                    responseStr = EntityUtils.toString(response.getEntity());
                    Log.d(TAG, "outcome = " + responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "[" + responseStr + "]";
            }


        protected void onPostExecute(String result) {
            // TODO: check this.exception
            // TODO: do something with the feed
            dialog.dismiss();
            try {
                JSONArray json = new JSONArray( result);

                JSONObject jsonobj = json.getJSONObject(0);
                Log.d("error out", "in onPostExecute results123 : " + result);
                String message = jsonobj.getString("message");

                if(message.equals("link_sent")){

                    Toast.makeText(getActivity(), "Activation link has been sent to your mail!", Toast.LENGTH_LONG).show();

                    email = (EditText) rootview.findViewById(R.id.username);
                    name = (EditText) rootview.findViewById(R.id.name);
                    pass = (EditText) rootview.findViewById(R.id.password);
                    repass = (EditText) rootview.findViewById(R.id.repass);
                    phone = (EditText) rootview.findViewById(R.id.phone);
                    dob = (EditText) rootview.findViewById(R.id.dob);
                    femaleBtn = (RadioButton) rootview.findViewById(R.id.gender_female);
                    maleBtn = (RadioButton) rootview.findViewById(R.id.gender_male);
                    city = (EditText) rootview.findViewById(R.id.city);


                    String[] args = new String[7];

                    args[0] = email.getText().toString().trim();


                    args[1] = name.getText().toString().trim();
                    args[2] = pass.getText().toString().trim();
                    args[3] = phone.getText().toString().trim();

                    args[5] = dob.getText().toString().trim();

                    if(femaleBtn.isChecked()) {
                        args[4] = "female";
                    } else if(maleBtn.isChecked()) {
                        args[4] = "male";
                    }

                    args[6] = city.getText().toString().trim();
                    new RetrieveFeedTask().execute(args);



                }else if(message.equals("link_failed")){
                    //Log.d("error out", "in onPostExecute message11111 : " + message);
                    Toast.makeText(getActivity(), "Try Again!!!!", Toast.LENGTH_LONG).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;



        @Override
        protected String doInBackground(String[] args) {

            DefaultHttpClient client = new DefaultHttpClient();
           // String url = "http://172.16.32.54:8888/rest/user/registration/";
            String url = "http://freecourierservice.appspot.com/rest/user/registration/";
            HttpPost request = new HttpPost(url);
            String responseStr = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair("email", args[0]));
                nameValuePairs.add(new BasicNameValuePair("fullname", args[1]));
                nameValuePairs.add(new BasicNameValuePair("pwd", args[2]));
                nameValuePairs.add(new BasicNameValuePair("phone", args[3]));
                nameValuePairs.add(new BasicNameValuePair("gender", args[4]));
                nameValuePairs.add(new BasicNameValuePair("dob", args[5]));
                nameValuePairs.add(new BasicNameValuePair("city", args[6]));


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(request);
                Log.d(TAG, "input = " + args[0] + " - " + args[1] + " - " + args[2] + " - " + args[3] + " - " + args[4] + " - " + args[5] + " - " + args[6]);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.d(TAG, "outcome = " + responseStr);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "["+responseStr+"]";
        }

        protected void onPostExecute(String result) {
            // TODO: check this.exception
            // TODO: do something with the feed
            try {
                JSONArray json = new JSONArray( result);

                JSONObject jsonobj = json.getJSONObject(0);
                Log.d("error out", "in onPostExecute results123 : " + result);
                String message = jsonobj.getString("message");
               // Log.d("error out", "in onPostExecute message : " + message);
                if(message.equals("success")){
                    //Log.d("1111error out", "in onPostExecute message : " + message);
                    Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }else if(message.equals("already registered")){
                    //Log.d("error out", "in onPostExecute message11111 : " + message);
                    Toast.makeText(getActivity(), "Already registered", Toast.LENGTH_LONG).show();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.mipmap.success : R.mipmap.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
