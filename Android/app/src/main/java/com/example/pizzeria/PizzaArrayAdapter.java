package com.example.pizzeria;
import android.app.ActionBar;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

//custom adapter for mascot images
public class PizzaArrayAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public PizzaArrayAdapter(Context c, List<Integer> pizzas) {
        mContext = c;
        mThumbIds = pizzas;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        final float scale = mContext.getResources().getDisplayMetrics().density;

        LinearLayout listEntry = new LinearLayout(mContext);
        listEntry.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        listEntry.setOrientation(LinearLayout.HORIZONTAL);

        ImageView pizzaImage = new ImageView(mContext);
        pizzaImage.setLayoutParams(new FrameLayout.LayoutParams((int)(125 * scale * 0.5f), (int)(125 * scale * 0.5f)));
        pizzaImage.setId(position);
        pizzaImage.setImageResource(mThumbIds.get(position));
        pizzaImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView firstEmptyTextView = new TextView(mContext);
        LinearLayout.LayoutParams firstEmptyTextParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        firstEmptyTextParams.weight = 1;
        firstEmptyTextView.setLayoutParams(firstEmptyTextParams);

        TextView pizzaName = new TextView(mContext);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.topMargin = (int)(45 * scale * 0.5f);
        nameParams.leftMargin = (int)(40 * scale * 0.5f);
        pizzaName.setLayoutParams(nameParams);
        pizzaName.setGravity(Gravity.RIGHT);
        pizzaName.setText("Some pizza name");
        pizzaName.setTextColor(mContext.getResources().getColor(R.color.black));

        TextView emptyTextView = new TextView(mContext);
        LinearLayout.LayoutParams emptyTextParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        emptyTextParams.weight = 1;
        emptyTextView.setLayoutParams(emptyTextParams);

        TextView pizzaPrice = new TextView(mContext);
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        priceParams.topMargin = (int)(45 * scale * 0.5f);
        priceParams.leftMargin = (int)(40 * scale * 0.5f);
        pizzaPrice.setLayoutParams(priceParams);
        pizzaPrice.setGravity(Gravity.RIGHT);
        pizzaPrice.setText("20€");
        pizzaPrice.setTextColor(mContext.getResources().getColor(R.color.black));

        ImageView cartButton = new ImageView(mContext);
        LinearLayout.LayoutParams cartButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cartButtonParams.rightMargin = (int) (20 * scale * 0.5f);
        cartButtonParams.topMargin = (int) (40 * scale * 0.5f);
        cartButton.setLayoutParams(cartButtonParams);
        cartButton.setImageResource(R.drawable.ic_add_to_cart);

        listEntry.addView(pizzaImage);
        listEntry.addView(pizzaName);
        listEntry.addView(firstEmptyTextView);
        listEntry.addView(pizzaPrice);
        listEntry.addView(emptyTextView);
        listEntry.addView(cartButton);

        return listEntry;
    }

    // Keep all Images in array
    private List<Integer> mThumbIds;
}
