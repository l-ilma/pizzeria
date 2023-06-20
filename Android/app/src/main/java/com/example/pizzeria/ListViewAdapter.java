package com.example.pizzeria;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.ui.dialog.DialogItemsAdapter;
import com.example.pizzeria.ui.dialog.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//custom adapter for mascot images
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    // Keep all Images in array
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

        final float scale = context.getResources().getDisplayMetrics().density;

        LinearLayout listEntry = new LinearLayout(context);
        listEntry.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        listEntry.setOrientation(LinearLayout.HORIZONTAL);

        ImageView pizzaImage = new ImageView(context);
        pizzaImage.setLayoutParams(new FrameLayout.LayoutParams(
                (int) (125 * scale * 0.5f),
                (int) (125 * scale * 0.5f)));

        pizzaImage.setId(position);
        pizzaImage.setImageResource(products.get(position).staticId);
        pizzaImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView firstEmptyTextView = new TextView(context);
        LinearLayout.LayoutParams firstEmptyTextParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        firstEmptyTextParams.weight = 1;
        firstEmptyTextView.setLayoutParams(firstEmptyTextParams);

        TextView pizzaName = new TextView(context);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                (int) (200 * scale * 0.5f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.topMargin = (int) (45 * scale * 0.5f);
        nameParams.leftMargin = (int) (40 * scale * 0.5f);

        pizzaName.setLayoutParams(nameParams);
        pizzaName.setGravity(Gravity.RIGHT);
        pizzaName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        pizzaName.setText(products.get(position).name);
        pizzaName.setTextColor(context.getResources().getColor(R.color.black));

        TextView emptyTextView = new TextView(context);
        LinearLayout.LayoutParams emptyTextParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        emptyTextParams.weight = 1;
        emptyTextView.setLayoutParams(emptyTextParams);

        TextView pizzaPrice = new TextView(context);
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        priceParams.topMargin = (int) (45 * scale * 0.5f);
        priceParams.leftMargin = (int) (40 * scale * 0.5f);

        pizzaPrice.setLayoutParams(priceParams);
        pizzaPrice.setGravity(Gravity.RIGHT);
        pizzaPrice.setText(Float.toString(products.get(position).price));
        pizzaPrice.setTextColor(context.getResources().getColor(R.color.black));

        ImageView cartButton = new ImageView(context);
        LinearLayout.LayoutParams cartButtonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cartButtonParams.rightMargin = (int) (20 * scale * 0.5f);
        cartButtonParams.topMargin = (int) (40 * scale * 0.5f);
        cartButton.setLayoutParams(cartButtonParams);
        cartButton.setImageResource(R.drawable.ic_add_to_cart);

        View.OnClickListener onCartButtonClick = view -> {
            addItemToBasket(products.get(position));
        };
        cartButton.setOnClickListener(onCartButtonClick);

        View.OnClickListener onCustomPizzaClick = v -> {
            showCustomPizzaDialog(products.get(position));
        };

        if (Objects.equals(products.get(position).name, "Custom")) {
            listEntry.setOnClickListener(onCustomPizzaClick);

            priceParams.rightMargin = (int) (155 * scale * 0.5f);
            pizzaPrice.setLayoutParams(priceParams);

            listEntry.addView(pizzaImage);
            listEntry.addView(pizzaName);
            listEntry.addView(firstEmptyTextView);
            listEntry.addView(pizzaPrice);
            return listEntry;
        }

        listEntry.addView(pizzaImage);
        listEntry.addView(pizzaName);
        listEntry.addView(firstEmptyTextView);
        listEntry.addView(pizzaPrice);
        listEntry.addView(emptyTextView);
        listEntry.addView(cartButton);

        return listEntry;
    }

    void showCustomPizzaDialog(Product product) {
        List<Item> pizzaToppings = ((MainActivity)context).pizzaToppings.stream().map(i -> new Item(i.name)).collect(Collectors.toList());
        List<Item> selectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_toppings);
        DialogItemsAdapter adapter = new DialogItemsAdapter(context.getApplicationContext(), android.R.layout.select_dialog_multichoice, pizzaToppings);
        builder.setAdapter(adapter,null);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
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
            @Override
            public void onClick(View v) {
                if (selectedItems.size() == 0) {
                    Toast.makeText(context, "You must select at least one topping", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: save ingredients
                    addItemToBasket(product);
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
                for (Item it: selectedItems) {
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
        BasketData basketData = new BasketData(product.name, product.staticId,
                1, product.price);
        Basket.getInstance().addItem(basketData);
        Toast.makeText(context, "Item added to the basket", Toast.LENGTH_SHORT).show();
    }
}
