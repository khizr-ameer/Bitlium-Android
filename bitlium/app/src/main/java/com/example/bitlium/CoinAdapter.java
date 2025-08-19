package com.example.bitlium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    private Context context;
    private List<Coin> coinList;
    private OnCoinClickListener listener;

    public interface OnCoinClickListener {
        void onCoinClick(Coin coin);
    }

    public CoinAdapter(Context context, List<Coin> coinList, OnCoinClickListener listener) {
        this.context = context;
        this.coinList = coinList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coin, parent, false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        Coin coin = coinList.get(position);
        holder.nameText.setText(coin.getName());
        holder.symbolText.setText(coin.getSymbol().toUpperCase());
        holder.priceText.setText("$" + String.format("%.2f", coin.getCurrent_price()));

        Glide.with(context)
                .load(coin.getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCoinClick(coin);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, symbolText, priceText;
        ImageView imageView;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.coinName);
            symbolText = itemView.findViewById(R.id.coinSymbol);
            priceText = itemView.findViewById(R.id.coinPrice);
            imageView = itemView.findViewById(R.id.coinImage);
        }
    }
}
