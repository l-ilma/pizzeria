package com.example.pizzeria.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.BasketData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{

    private List<BasketData> basketContent = new ArrayList<>();

    public CheckoutAdapter(BasketData[] basketContent){
        this.basketContent = Arrays.asList(basketContent);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.checkout_item, parent, false);
        return new CheckoutAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(CheckoutAdapter.ViewHolder holder, int position) {
        final BasketData data = basketContent.get(position);
        holder.textView.setText(data.getProduct().name);
        holder.imageView.setImageResource(data.getProduct().staticId);
        holder.itemNumbers.setText(data.getQuantity() + "x");
        holder.numberView.setText(data.getProduct().price + "€");
    }

    @Override
    public int getItemCount() {
        return basketContent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView numberView;

        public RelativeLayout layout;
        public TextView itemNumbers;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.itemImageView);
            this.textView = itemView.findViewById(R.id.itemNameView);
            this.numberView = itemView.findViewById(R.id.priceView);
            layout = itemView.findViewById(R.id.checkoutLayout);
            this.itemNumbers = itemView.findViewById(R.id.itemCountView);

        }
    }
}
