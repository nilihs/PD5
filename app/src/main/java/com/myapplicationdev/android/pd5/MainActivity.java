package com.myapplicationdev.android.pd5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lvReceipt;
    private ArrayList<Receipt> alReceipt;
    private ArrayAdapter<Receipt> aaReceipt;
    private AsyncHttpClient client;

    private String loginId;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();

        lvReceipt = (ListView) findViewById(R.id.listViewReceipt);
        alReceipt = new ArrayList<Receipt>();

        aaReceipt = new ReceiptAdapter(this, R.layout.receipt_row, alReceipt);
        lvReceipt.setAdapter(aaReceipt);

        Intent i = getIntent();
        loginId = i.getStringExtra("loginId");
        apiKey = i.getStringExtra("apikey");

        lvReceipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Receipt selectedReceipt = alReceipt.get(position);

                Intent intent = new Intent(MainActivity.this, ViewReceiptDetailsActivity.class);
                intent.putExtra("receiptId", selectedReceipt.getReceiptId());


                startActivity(intent);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        alReceipt.clear();

        RequestParams params = new RequestParams();
        params.put("loginId",loginId);
        params.put("apikey", apiKey);

        String url = "http://10.0.2.2/trackerApp/getListOfReceipt.php";

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("JSON Results: ", response.toString());

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);

                        int receiptId = jsonObj.getInt("id");
                        String title = jsonObj.getString("title");
                        String description = jsonObj.getString("description");
                        String cost = jsonObj.getString("cost");

                        Receipt receipt = new Receipt(receiptId, cost, title, description);
                        alReceipt.add(receipt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aaReceipt = new ReceiptAdapter(MainActivity.this, R.layout.receipt_row, alReceipt);
                lvReceipt.setAdapter(aaReceipt);

                lvReceipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Receipt selectedReceipt = alReceipt.get(position);
                        Intent i = new Intent(MainActivity.this, ViewReceiptDetailsActivity.class);
                        i.putExtra("receiptId", selectedReceipt.getReceiptId());
                        i.putExtra("loginId", loginId);
                        i.putExtra("apikey", apiKey);
                        startActivity(i);

                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_add) {

            Intent intent = new Intent(MainActivity.this, CreateReceiptActivity.class);
            intent.putExtra("loginId", loginId);
            intent.putExtra("apikey", apiKey);
            startActivity(intent);
            return true;

        }

        if (id == R.id.menu_calculate) {

            Intent intent = new Intent(MainActivity.this, CalculateReceiptActivity.class);
            intent.putExtra("loginId", loginId);
            intent.putExtra("apikey", apiKey);

            int totalCost = 0;

            for(int i = 0; i < alReceipt.size(); i++) {

                totalCost += Integer.parseInt(alReceipt.get(i).getCost());

            }

            intent.putExtra("totalCost", totalCost);

            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
