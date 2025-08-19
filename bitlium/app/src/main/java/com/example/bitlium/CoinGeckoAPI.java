package com.example.bitlium;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinGeckoAPI {
    @GET("coins/markets")
    Call<List<Coin>> getTopCoins(
            @Query("vs_currency") String currency,
            @Query("order") String order,
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query("sparkline") boolean sparkline
    );

    default Call<List<Coin>> getTopCoins() {
        return getTopCoins("usd", "market_cap_desc", 25, 1, false);
    }
}
