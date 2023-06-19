package com.example.pizzeria.Checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.BasketAdapter;
import com.example.pizzeria.basket.BasketData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{

    private BasketAdapter basketAdapter; // needed to fetch data from Basket
    private List<BasketData> basket_content_final = new ArrayList<>();

    public CheckoutAdapter(BasketData[] basket_content){
        this.basket_content_final = Arrays.asList(basket_content);
    }

    public void setBasketAdapter(BasketAdapter basketAdapter) {
        this.basketAdapter = basketAdapter;
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
        final BasketData data = basket_content_final.get(position);
        holder.textView.setText(data.getDescription());
        holder.imageView.setImageResource(data.getImgId());
        holder.itemNumbers.setText(data.getQuantity() + "x");
        holder.numberView.setText(data.getPrice() + "€");
    }

    @Override
    public int getItemCount() {
        return basket_content_final.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView numberView;

        public RelativeLayout layout;
        public TextView itemNumbers;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView_checkout);
            this.textView = itemView.findViewById(R.id.textView_checkout);
            this.numberView = itemView.findViewById(R.id.numberView_checkout);
            layout = itemView.findViewById(R.id.checkoutLayout);
            this.itemNumbers = itemView.findViewById(R.id.item_number);

        }
    }
}
