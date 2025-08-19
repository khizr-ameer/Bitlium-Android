package com.example.bitlium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuySellActivity extends AppCompatActivity {

    private TextView coinNameTextView, coinPriceTextView;
    private EditText amountEditText;
    private Button buyButton, sellButton;

    private String coinName, coinSymbol;
    private double coinPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);

        coinNameTextView = findViewById(R.id.coinNameTextView);
        coinPriceTextView = findViewById(R.id.coinPriceTextView);
        amountEditText = findViewById(R.id.amountEditText);
        buyButton = findViewById(R.id.buyButton);
        sellButton = findViewById(R.id.sellButton);

        // Get data from intent
        Intent intent = getIntent();
        coinName = intent.getStringExtra("coin_name");
        coinSymbol = intent.getStringExtra("coin_symbol");
        coinPrice = intent.getDoubleExtra("coin_price", 0);

        // Set the coin info in UI
        coinNameTextView.setText(coinName + " (" + coinSymbol.toUpperCase() + ")");
        coinPriceTextView.setText("Price: $" + coinPrice);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBuySell(true);
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBuySell(false);
            }
        });
    }

    private void handleBuySell(boolean isBuy) {
        String amountStr = amountEditText.getText().toString();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount <= 0) {
            Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        // For now, just show a confirmation Toast
        String action = isBuy ? "Bought" : "Sold";
        Toast.makeText(this, action + " " + amount + " " + coinSymbol.toUpperCase(), Toast.LENGTH_LONG).show();

        // TODO: Implement real buy/sell logic here, update balance, transaction history, etc.
    }
}
