package com.example.android.otpapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VarifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);

        //get the value entered by the user
        Pinview pinview = (Pinview) findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                String otp = pinview.getValue();
                String myUrl  = "https://www-feacbook-com.000webhostapp.com/verify.php?otp=" + otp;
                Log.v("url", myUrl);
                asyncTask mytask = new asyncTask();
                mytask.execute(myUrl);

            }
        });


    }

private class asyncTask extends AsyncTask<String, String, String> {
    //async task to manage sending data request and receving the json response and parsing it.

    @Override
    protected String doInBackground(String... url) {
        String Url = url[0];
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().cacheControl(new CacheControl.Builder().noCache().build()).url(Url).build();
        Response response = null;
        final String jsonData;
        String auth = null;
        try {
            response = okHttpClient.newCall(request).execute();
            try {
                jsonData = response.body().string().toString();
                Log.v("json", String.valueOf(jsonData));
                JSONObject Jobject = new JSONObject(jsonData);
                Log.v("json", String.valueOf(Jobject));
                JSONObject authObject = Jobject.getJSONObject("auth");
                Log.v("json", String.valueOf(authObject));
                auth = authObject.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return auth;
    }

    @Override
    protected void onPostExecute(String auth) {
        //determines if the otp entered by the user is same as that of the server.

        if (Objects.equals(auth, "1")) {
            Toast.makeText(getApplicationContext(), "Verification complete, Successfully", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Some error occurred ...", Toast.LENGTH_SHORT).show();

    }
}
}