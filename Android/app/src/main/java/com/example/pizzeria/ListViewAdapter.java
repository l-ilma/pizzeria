package com.example.pizzeria;
import android.content.Context;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pizzeria.basket.Basket;
import com.example.pizzeria.basket.BasketData;
import com.example.pizzeria.models.Product;

import java.util.List;

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
        return null;
    }

    public long getItemId(int position) {
        return 0;
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
                (int)(125 * scale * 0.5f),
                (int)(125 * scale * 0.5f)));

        pizzaImage.setId(position);
        pizzaImage.setImageResource(products.get(position).id);
        pizzaImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView firstEmptyTextView = new TextView(context);
        LinearLayout.LayoutParams firstEmptyTextParams = new LinearLayout.LayoutParams(0,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
        firstEmptyTextParams.weight = 1;
        firstEmptyTextView.setLayoutParams(firstEmptyTextParams);

        TextView pizzaName = new TextView(context);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                (int)(200 * scale * 0.5f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.topMargin = (int)(45 * scale * 0.5f);
        nameParams.leftMargin = (int)(40 * scale * 0.5f);

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
        priceParams.topMargin = (int)(45 * scale * 0.5f);
        priceParams.leftMargin = (int)(40 * scale * 0.5f);

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
            Product currentProduct = products.get(position);
            BasketData basketData = new BasketData(currentProduct.name, currentProduct.id,
                                          1, currentProduct.price);
            Basket.getInstance().addItem(basketData);
        };
        cartButton.setOnClickListener(onCartButtonClick);

        listEntry.addView(pizzaImage);
        listEntry.addView(pizzaName);
        listEntry.addView(firstEmptyTextView);
        listEntry.addView(pizzaPrice);
        listEntry.addView(emptyTextView);
        listEntry.addView(cartButton);

        return listEntry;
    }

}
