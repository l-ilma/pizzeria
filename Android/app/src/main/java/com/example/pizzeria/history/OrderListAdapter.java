package com.example.pizzeria.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketActivity;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.model.OrderWithProducts;
import com.example.pizzeria.utils.Utilities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    public List<OrderWithProducts> items = new ArrayList<>();
    public Context context;

    public OrderListAdapter(Context context, List<OrderWithProducts> items){
        this.items.addAll(items);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint({"NewApi"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderWithProducts orderWithProducts = items.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        holder.dateText.setText(dateFormat.format(orderWithProducts.order.createdAt));
        holder.priceText.setText(decimalFormat.format(orderWithProducts.order.price) + "€");
        holder.productsText.setText(Utilities.getProductsAsString(orderWithProducts));
        holder.statusText.setText(orderWithProducts.order.status.name());
        holder.addressText.setText(orderWithProducts.order.address);
        holder.plzText.setText(orderWithProducts.order.zip);
        holder.noteText.setText(orderWithProducts.order.note);
        holder.redoOrderButton.setOnClickListener(view -> {
            OrderWithProducts orderWithProducts1 = items.get(holder.getAdapterPosition());
            HashMap<String, Integer> productCountMap = Utilities.getProductCounts(orderWithProducts1);

            Basket.getInstance().clearItems();
            for(Product product : orderWithProducts1.products){
                Basket.getInstance().addItem(new BasketData(product,
                        productCountMap.get(product.name)));
            }

            Intent basketActivity = new Intent(context, BasketActivity.class);
            basketActivity.putExtra("address", orderWithProducts1.order.address);
            basketActivity.putExtra("zip", orderWithProducts1.order.zip);
            basketActivity.putExtra("note", orderWithProducts1.order.note);
            basketActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(basketActivity);
        });
        if(orderWithProducts.order.status.name().equals("ORDERED")){
            holder.statusText.setTextColor(Color.parseColor("#dc2626"));
        }
        else if(orderWithProducts.order.status.name().equals("PROCESSED")){
            holder.statusText.setTextColor(Color.parseColor("#FCD12A"));
        }
        else if(orderWithProducts.order.status.name().equals("DELIVERED")){
            holder.statusText.setTextColor(Color.parseColor("#0d9488"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView dateText;
        public TextView productsText;
        public TextView priceText;
        public TextView statusText;
        public TextView addressText;
        public TextView plzText;
        public TextView noteText;
        public ImageButton redoOrderButton;
        public RelativeLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dateText = itemView.findViewById(R.id.orderDate);
            this.productsText = itemView.findViewById(R.id.orderedProducts);
            this.priceText = itemView.findViewById(R.id.orderPrice);
            this.statusText = itemView.findViewById(R.id.order_status);
            this.addressText = itemView.findViewById(R.id.order_address);
            this.plzText = itemView.findViewById(R.id.order_plz);
            this.itemLayout = itemView.findViewById(R.id.orderLayout);
            this.noteText = itemView.findViewById(R.id.order_note);
            this.redoOrderButton = itemView.findViewById(R.id.redo_button);
        }

    }
}


