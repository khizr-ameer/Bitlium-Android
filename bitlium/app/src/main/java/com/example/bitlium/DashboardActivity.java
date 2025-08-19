package com.example.bitlium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView coinRecyclerView;
    private CoinAdapter coinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        coinRecyclerView = findViewById(R.id.coinRecyclerView);
        coinRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchCoinData();
    }

    private void fetchCoinData() {
        CoinGeckoAPI api = ApiClient.getRetrofitInstance().create(CoinGeckoAPI.class);
        Call<List<Coin>> call = api.getTopCoins();

        call.enqueue(new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, Response<List<Coin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Coin> coinList = response.body();

                    coinAdapter = new CoinAdapter(DashboardActivity.this, coinList, coin -> {
                        // Start BuySellActivity and pass coin info
                        Intent intent = new Intent(DashboardActivity.this, BuySellActivity.class);
                        intent.putExtra("coin_name", coin.getName());
                        intent.putExtra("coin_symbol", coin.getSymbol());
                        intent.putExtra("coin_price", coin.getCurrent_price());
                        startActivity(intent);
                    });

                    coinRecyclerView.setAdapter(coinAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {
                Log.e("DashboardActivity", "Failed to fetch coins: " + t.getMessage());
            }
        });
    }
}
