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

public class ViewReceiptDetailsActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etCost;
    private Button btnUpdate, btnDelete;
    private int receiptId;
    private int loginId;
    private String apiKey;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt_details);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etCost = findViewById(R.id.etCost);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intentReceived = getIntent();
        receiptId = intentReceived.getIntExtra("receiptId",0);
        loginId = intentReceived.getIntExtra("loginId", 0);
        apiKey = intentReceived.getStringExtra("apikey");

        client = new AsyncHttpClient();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put("loginId", loginId);
                params.put("apikey", apiKey);
                params.put("id", receiptId);
                params.put("title", etTitle.getText().toString());
                params.add("description", etDescription.getText().toString());
                params.add("cost", etCost.getText().toString());

                String url = "http://10.0.2.2/trackerApp/updateReceipt.php";

                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {

                            Log.i("JSON Results: ", response.toString());

                            Boolean authorized = response.getBoolean("authorized");
                            Boolean status = response.getBoolean("status");
                            String message = response.getString("message");

                            if ((authorized != false) && status != false) {
                                Toast.makeText(ViewReceiptDetailsActivity.this, message, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(ViewReceiptDetailsActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put("loginId", loginId);
                params.put("apikey", apiKey);
                params.put("id", receiptId);

                String url = "http://10.0.2.2/trackerApp/deleteReceipt.php";

                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Log.i("JSON Results: ", response.toString());

                            Boolean authorized = response.getBoolean("authorized");
                            Boolean status = response.getBoolean("status");
                            String message = response.getString("message");

                            if ((authorized != false) && status != false) {
                                Toast.makeText(ViewReceiptDetailsActivity.this, message, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(ViewReceiptDetailsActivity.this, message, Toast.LENGTH_LONG).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        receiptId = intent.getIntExtra("receiptId", -1);

        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginId", loginId);
        params.put("apikey", apiKey);
        params.put("id", receiptId);

        String url = "http://10.0.2.2/trackerApp/getReceiptDetails.php?id=" + receiptId;

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.i("JSON Results: ", response.toString());

                    String title = response.getString("title");
                    String description = response.getString("description");
                    String cost = response.getString("cost");

                    etTitle.setText(title);
                    etDescription.setText(description);
                    etCost.setText(cost);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}