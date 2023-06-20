package com.example.pizzeria.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.Nullable;

import java.util.List;

public class DialogItemsAdapter extends ArrayAdapter {
    public DialogItemsAdapter(Context context, int layoutId, List<Item> items) {
        super(context.getApplicationContext(), layoutId, items);
        this.items = items;
        this.context = context;
        this.layoutId = layoutId;
    }
    ViewHolder holder;
    List<Item> items;
    Context context;
    int layoutId;

    class ViewHolder {
        CheckedTextView checkedTextView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, null);
            holder = new ViewHolder();
            holder.checkedTextView = (CheckedTextView) convertView;
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkedTextView.setText(items.get(position).name);
        holder.checkedTextView.setChecked(items.get(position).selected);
        holder.checkedTextView.setEnabled(items.get(position).enabled);
        return convertView;
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    public int getItemCount() {
        return items.size();
    }
}
