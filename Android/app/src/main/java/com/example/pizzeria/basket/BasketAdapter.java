package com.example.pizzeria.basket;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.entity.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private final List<BasketData> basket = new ArrayList<>();
    private final int deliveryFee = 2;

    public BasketAdapter(BasketData[] basket) {
        this.basket.addAll(Arrays.asList(basket));
    }

    public int getDeliveryFee() {
        return deliveryFee;
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
        final Product product = data.getProduct();
        holder.textView.setText(product.name);
        holder.imageView.setImageResource(product.staticId);
        holder.numberOfProducts.setText(String.valueOf(data.getQuantity()));
        holder.numberView.setText(product.price + "€");

        if (product.name.equals("Custom")) {
            String toppings = product.ingredients.replace(",", ", ");
            toppings = toppings.substring(0,1).toUpperCase() + toppings.substring(1).toLowerCase();
            holder.toppingsCustom.setText(toppings);
            holder.toppingsCustom.setVisibility(View.VISIBLE);
        } else {
            holder.toppingsCustom.setVisibility(View.INVISIBLE);
        }
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
            sum += basket.get(i).getProduct().price * basket.get(i).getQuantity();
        }
        return sum + deliveryFee;
    }

    public void changeItemCount(int position, int quantity) {
        if (position > 0)
            basket.get(position).setQuantity(quantity);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView toppingsCustom;
        public TextView numberView;
        public RelativeLayout layout;
        public EditText numberOfProducts;
        public Button increment;
        public Button decrement;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            this.toppingsCustom = itemView.findViewById(R.id.toppingsCustom);
            this.numberView = itemView.findViewById(R.id.numberView);
            layout = itemView.findViewById(R.id.basketLayout);

            View numberPicker = itemView.findViewById(R.id.numberPicker);
            numberOfProducts = numberPicker.findViewById(R.id.numberOfProducts);
            increment = numberPicker.findViewById(R.id.incrementButton);
            decrement = numberPicker.findViewById(R.id.decrementButton);

            decrement.setOnClickListener((View view) -> {
                int currentNum = Integer.parseInt(numberOfProducts.getText().toString());
                updateCount(currentNum - 1);
            });

            increment.setOnClickListener((View view) -> {
                int currentNum = Integer.parseInt(numberOfProducts.getText().toString());
                updateCount(currentNum + 1);
            });

            numberOfProducts.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    BasketData[] basketItems = Basket.getInstance().getBasketItems();
                    int newVal;

                    try {
                        newVal = Integer.parseInt(charSequence.toString());
                    } catch (Exception e) {
                        return;
                    }

                    Basket.getInstance().updateItemCount(basketItems[getAdapterPosition()], newVal);
                    ((BasketActivity) layout.getContext()).changeItemCount(getAdapterPosition(), newVal);


                    if (newVal <= 0 && getAdapterPosition() >= 0) {
                        Basket.getInstance().removeItem(basketItems[getAdapterPosition()]);
                        ((BasketActivity) layout.getContext()).removeItem(getAdapterPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //
                }
            });
        }

        private void updateCount(int newVal) {
            numberOfProducts.setText(String.valueOf(newVal));
        }
    }

    public List<BasketData> getBasketData(){
        return basket;
    }


}
