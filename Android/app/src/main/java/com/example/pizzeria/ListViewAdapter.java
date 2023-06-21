package com.example.pizzeria;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.ui.dialog.DialogItemsAdapter;
import com.example.pizzeria.ui.dialog.Item;
import com.example.pizzeria.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    // Constructor
    public ListViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public int getCount() {
        return products.size();
    }

    public Object getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return position == getCount() - 1 && Objects.equals(products.get(position).name, "Custom");
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_view_item, parent, false);
        }

        Product product = products.get(position);

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        itemImage.setImageResource(product.staticId);

        TextView itemName = convertView.findViewById(R.id.itemName);
        itemName.setText(product.name);

        TextView itemIngredients = convertView.findViewById(R.id.itemIngredients);
        if (product.ingredients.length() != 0) {
            itemIngredients.setVisibility(View.VISIBLE);
            itemIngredients.setText(product.ingredients);
        } else {
            itemIngredients.setVisibility(View.GONE);
        }

        TextView itemPrice = convertView.findViewById(R.id.itemPrice);
        itemPrice.setText(product.price + "€");

        ImageView basket = convertView.findViewById(R.id.basket);

        if (Objects.equals(product.name, "Custom")) {
            basket.setVisibility(View.INVISIBLE);
            itemIngredients.setVisibility(View.VISIBLE);
            itemIngredients.setText(R.string.make_custom_pizza);

            View.OnClickListener onCustomPizzaClick = v -> {
                showCustomPizzaDialog(products.get(position));
            };

            convertView.setOnClickListener(onCustomPizzaClick);
        } else {
            basket.setVisibility(View.VISIBLE);
        }

        View.OnClickListener onCartButtonClick = view -> {
            addItemToBasket(products.get(position));
        };
        basket.setOnClickListener(onCartButtonClick);

        return convertView;
    }

    void showCustomPizzaDialog(Product product) {
        List<Item> pizzaToppings = Utilities.ingredients.stream().map(Item::new).collect(Collectors.toList());
        List<Item> selectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_toppings);
        DialogItemsAdapter adapter = new DialogItemsAdapter(context.getApplicationContext(), android.R.layout.select_dialog_multichoice, pizzaToppings);
        builder.setAdapter(adapter, null);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alert = builder.create();
        alert.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int index, long l) {
                onCustomPizzaToppingClick(adapterView, view, index, selectedItems);
            }
        });
        alert.show();

        // Override BUTTON_POSITIVE onClick
        // (necessary because dialog closes automatically after clicking on OK otherwise)
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (selectedItems.size() == 0) {
                    Toast.makeText(context, "You must select at least one topping", Toast.LENGTH_SHORT).show();
                } else {
                    Product newPizza = new Product(product.name, product.price, product.staticId,
                            String.join(",", selectedItems.stream()
                                    .map(i -> i.name).collect(Collectors.toList())), 1);
                    addItemToBasket(newPizza);
                    alert.dismiss();
                }
            }
        });

    }

    void onCustomPizzaToppingClick(AdapterView<DialogItemsAdapter> adapterView, View view, int index, List<Item> selectedItems) {
        CheckedTextView checkedTextView = ((CheckedTextView) view);
        DialogItemsAdapter adapter = adapterView.getAdapter();
        Item item = adapter.getItem(index);
        if (checkedTextView.isEnabled()) {
            checkedTextView.setChecked(!checkedTextView.isChecked());
            item.selected = checkedTextView.isChecked();
        }

        if (checkedTextView.isChecked()) {
            selectedItems.add(item);
        } else {
            selectedItems.remove(item);
        }

        if (selectedItems.size() >= 5) { // limit reached
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getItem(i).enabled = selectedItems.contains(adapter.getItem(i));
            }
            for (int i = 0; i < adapterView.getChildCount(); i++) {
                CheckedTextView childView = (CheckedTextView) adapterView.getChildAt(i);
                boolean found = false;
                for (Item it : selectedItems) {
                    if (Objects.equals(it.name, childView.getText().toString())) {
                        found = true;
                        break;
                    }
                }
                childView.setEnabled(found);
            }
        } else {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getItem(i).enabled = true;
            }
            for (int i = 0; i < adapterView.getChildCount(); i++) {
                CheckedTextView childView = (CheckedTextView) adapterView.getChildAt(i);
                childView.setEnabled(true);
            }
        }
    }

    void addItemToBasket(Product product) {
        BasketData basketData = new BasketData(product, 1);
        Basket.getInstance().addItem(basketData);
        Toast.makeText(context, "Item added to the basket", Toast.LENGTH_SHORT).show();
    }
}
