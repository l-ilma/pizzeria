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

        LinearLayout listEntry = new LinearLayout(mContext);
        listEntry.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        listEntry.setOrientation(LinearLayout.HORIZONTAL);

        ImageView pizzaImage = new ImageView(mContext);
        pizzaImage.setLayoutParams(new FrameLayout.LayoutParams(200, 200));
        pizzaImage.setId(position);
        pizzaImage.setImageResource(mThumbIds.get(position));
        pizzaImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView pizzaName = new TextView(mContext);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.topMargin = 40;
        nameParams.leftMargin = 40;
        pizzaName.setLayoutParams(nameParams);
        pizzaName.setGravity(Gravity.RIGHT);
        pizzaName.setText("Some pizza name");
        pizzaName.setTextColor(mContext.getResources().getColor(R.color.white));

        TextView emptyTextView = new TextView(mContext);
        LinearLayout.LayoutParams emptyTextParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        emptyTextParams.weight = 1;
        emptyTextView.setLayoutParams(emptyTextParams);

        TextView pizzaPrice = new TextView(mContext);
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        priceParams.topMargin = 40;
        priceParams.leftMargin = 40;
        pizzaPrice.setLayoutParams(priceParams);
        pizzaPrice.setGravity(Gravity.RIGHT);
        pizzaPrice.setText("20€");
        pizzaPrice.setTextColor(mContext.getResources().getColor(R.color.white));

        listEntry.addView(pizzaImage);
        listEntry.addView(pizzaName);
        listEntry.addView(emptyTextView);
        listEntry.addView(pizzaPrice);

        return listEntry;
    }

    // Keep all Images in array
    private List<Integer> mThumbIds;
}
