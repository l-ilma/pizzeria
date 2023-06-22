package com.example.pizzeria.history;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.BasketAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    public List<OrderWithProducts> items;

    public OrderListAdapter(List<OrderWithProducts> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderWithProducts orderWithProducts = items.get(position);
        holder.dateText.setText(orderWithProducts.order.createdAt.toString());
        holder.priceText.setText(orderWithProducts.order.price + "€");
        List<String> productNames = orderWithProducts.products.stream().map(p -> p.name).collect(Collectors.toList());
        holder.productsText.setText(String.join(",", productNames));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView dateText;
        public TextView productsText;
        public TextView priceText;
        public RelativeLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dateText = itemView.findViewById(R.id.orderDate);
            this.productsText = itemView.findViewById(R.id.orderedProducts);
            this.priceText = itemView.findViewById(R.id.orderPrice);
            this.itemLayout = itemView.findViewById(R.id.orderLayout);
        }
    }
}


