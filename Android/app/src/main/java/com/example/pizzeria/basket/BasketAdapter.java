package com.example.pizzeria.basket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private final List<BasketData> basket = new ArrayList<>();
    private final int deliveryFee = 2;

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public BasketAdapter(BasketData[] basket) {
        this.basket.addAll(Arrays.asList(basket));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(BasketAdapter.ViewHolder holder, int position) {
        final BasketData data = basket.get(position);
        holder.textView.setText(data.getDescription());
        holder.imageView.setImageResource(data.getImgId());
        holder.numberPicker.setValue(data.getQuantity());
        holder.numberView.setText(data.getPrice() + "€");
    }

    @Override
    public int getItemCount() {
        return basket.size();
    }

    public void removeItem(int position) {
        if (basket.size() <= position) {
            return;
        }
        basket.remove(position);
    }

    public float getSumOfCosts() {
        float sum = 0f;
        for (int i = 0; i < basket.size(); i++) {
            sum += basket.get(i).getPrice() * basket.get(i).getQuantity();
        }
        return sum + deliveryFee;
    }

    public void changeItemCount(int position, int quantity) {
        if(position > 0)
            basket.get(position).setQuantity(quantity);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageView;
        public TextView textView;
        public TextView numberView;
        public RelativeLayout layout;
        public NumberPicker numberPicker;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            this.numberView = itemView.findViewById(R.id.numberView);
            layout = itemView.findViewById(R.id.basketLayout);
            numberPicker = itemView.findViewById(R.id.numberPicker);

            if (numberPicker != null) {
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(10);
                numberPicker.setWrapSelectorWheel(true);
                numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                    BasketData[] basketItems = Basket.getInstance().getBasketItems();
                    if (newVal == 0) {
                        Basket.getInstance()
                                .removeItem(basketItems[getAdapterPosition()].getImgId());
                        ((BasketActivity) layout.getContext()).removeItem(getAdapterPosition());
                        return;
                    }
                    if (newVal != oldVal) {

                        if(getAdapterPosition() >= 0)
                            Basket.getInstance()
                                    .updateItemCount(basketItems[getAdapterPosition()].getImgId(), newVal);
                        ((BasketActivity) layout.getContext()).changeItemCount(getAdapterPosition(), newVal);
                    }
                });
            }

        }
    }

    
}
