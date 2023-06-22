package com.example.pizzeria.ui.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pizzeria.R;
import com.example.pizzeria.entity.Product;
import com.example.pizzeria.entity.ProductOrder;
import com.example.pizzeria.model.OrderWithProducts;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.ProductOrderRepository;
import com.example.pizzeria.utils.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

public class OrderArrayAdapter extends BaseAdapter {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy  hh:mm", Locale.GERMANY);
    private List<OrderWithProducts> orderWithProductsList;
    private Context context;

    private OrderRepository orderRepository;
    private ProductOrderRepository productOrderRepository;

    private Status updatedStatus;

    public OrderArrayAdapter(Context context, List<OrderWithProducts> orderWithProductsList) {
        this.context = context;
        this.orderWithProductsList = orderWithProductsList;
        orderRepository = new OrderRepository(context);
        productOrderRepository = new ProductOrderRepository(context);
    }

    @Override
    public int getCount() {
        return orderWithProductsList.size();
    }

    @Override
    public OrderWithProducts getItem(int i) {
        return orderWithProductsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.order_layout, parent, false);
        }

        OrderWithProducts current = orderWithProductsList.get(position);
        ((TextView) convertView.findViewById(R.id.orderId)).setText(String.valueOf(current.order.id));
        ((TextView) convertView.findViewById(R.id.status)).setText(current.order.status.name());
        ((TextView) convertView.findViewById(R.id.orderDate)).setText(DATE_FORMAT.format(current.order.createdAt));
        updatedStatus = current.order.status;

        convertView.setOnClickListener(view -> {
            showProcessOrderDialog(current, parent);
        });

        return convertView;
    }

    void showProcessOrderDialog(OrderWithProducts orderWithProducts, ViewGroup parent) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Order with id: " + orderWithProducts.order.id);

        View view = LayoutInflater.from(context).inflate(R.layout.order_dialog_layout, parent, false);
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(orderWithProducts.order.price));


        AsyncTask.execute(() -> {
            List<ProductOrder> refs = productOrderRepository.getAllProductOrderCrossRefs();
            StringJoiner stringJoiner = new StringJoiner(", ");

            for (Product p : orderWithProducts.products) {
                int quantity = refs.stream()
                        .filter(x -> x.orderId == orderWithProducts.order.id && x.productId == p.id)
                        .findFirst()
                        .get().quantity;

                stringJoiner.add(String.format("%s %d", p.name, quantity));
            }

            ((TextView) view.findViewById(R.id.products)).setText(stringJoiner.toString());
        });


        Status orderStatus = orderWithProducts.order.status;

        RadioButton orderedRadio = view.findViewById(R.id.ordered);
        RadioButton processedRadio = view.findViewById(R.id.process);
        RadioButton deliveredRadio = view.findViewById(R.id.delivery);


        orderedRadio.setChecked(orderStatus == Status.ORDERED);
        processedRadio.setChecked(orderStatus == Status.PROCESSED);
        deliveredRadio.setChecked(orderStatus == Status.DELIVERED);

        orderedRadio.setOnClickListener(radio -> {
            updatedStatus = Status.ORDERED;
        });

        processedRadio.setOnClickListener(radio -> {
            updatedStatus = Status.PROCESSED;
        });

        deliveredRadio.setOnClickListener(radio -> {
            updatedStatus = Status.DELIVERED;
        });

        builder.setView(view);


        builder.setNegativeButton("discard", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("save", (dialog, which) -> {
            AsyncTask.execute(() -> {
                orderRepository.updateStatus(orderWithProducts.order.id, updatedStatus);
            });
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private String getProductsAsString(OrderWithProducts orderWithProducts) {
        HashMap<String, Integer> productCountMap = new HashMap<>();

        StringJoiner productStringJoiner = new StringJoiner(", ");
        for (Map.Entry entry : productCountMap.entrySet()) {
            productStringJoiner.add(String.format("%s %dx", entry.getKey(), entry.getValue()));
        }

        return productStringJoiner.toString();
    }
}