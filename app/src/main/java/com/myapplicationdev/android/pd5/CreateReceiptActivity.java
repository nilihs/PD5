package com.myapplicationdev.android.pd5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateReceiptActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etCost;
    private Button btnCreate;
    private String loginId;
    private String apiKey;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etCost = findViewById(R.id.etCost);
        btnCreate = findViewById(R.id.btnCreate);

        Intent intentReceived = getIntent();
        loginId = intentReceived.getStringExtra("loginId");
        apiKey = intentReceived.getStringExtra("apikey");

        client = new AsyncHttpClient();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put("loginId",loginId);
                params.put("apikey",apiKey);
                params.put("title",etTitle.getText().toString());
                params.put("description", etDescription.getText().toString());
                params.put("cost", etCost.getText().toString());

                String url = "http://10.0.2.2/trackerApp/createReceipt.php";

                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Log.i("JSON Results: ", response.toString());
                            Boolean authenticated = response.getBoolean("authenticated");
                            Boolean status = response.getBoolean("status");
                            String message = response.getString("message");

                            if ((authenticated != false) && status != false) {
                                Toast.makeText(CreateReceiptActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CreateReceiptActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                finish();

            }
        });
    }
}