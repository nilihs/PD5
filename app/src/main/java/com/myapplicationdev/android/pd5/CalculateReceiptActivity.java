package com.myapplicationdev.android.pd5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculateReceiptActivity extends AppCompatActivity {

    TextView tvCalculate;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_receipt);

        tvCalculate = findViewById(R.id.tvCalculateCost);
        btnHome = findViewById(R.id.btnHome);

        Intent intentReceived = getIntent();
        int totalCost = intentReceived.getIntExtra("totalCost", 0);

        tvCalculate.setText("$" + totalCost);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CalculateReceiptActivity.this, MainActivity.class);
                startActivity(i);

            }
        });

    }
}
