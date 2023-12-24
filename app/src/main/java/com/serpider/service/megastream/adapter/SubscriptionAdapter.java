package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Subscription;
import com.serpider.service.megastream.util.DataSave;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>{
    private Context context;
    private List<Subscription> data;

    public SubscriptionAdapter(Context context, List<Subscription> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public SubscriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subscription, parent, false);
        return new SubscriptionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.MyViewHolder holder, int position) {
        Subscription subscription = data.get(position);

        holder.txtTitle.setText(subscription.getName());
        holder.txtPriod.setText(subscription.getPeriod());
        holder.txtDiscountPercent.setText(subscription.getDiscount_percent() + "% تخفیف");
        holder.txtDiscount.setText(String.valueOf(subscription.getDiscount()));
        holder.txtDiscount.setPaintFlags(holder.txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtPrice.setText(String.valueOf(subscription.getPrice()));

        int subscriptionType = subscription.getType();
        int color;

        if (subscriptionType >= 1 && subscriptionType <= 2) {
            color = context.getResources().getColor(R.color.theme_sub_tan);
        } else if (subscriptionType >= 3 && subscriptionType <= 5) {
            color = context.getResources().getColor(R.color.theme_sub_silver);
        } else if (subscriptionType >= 6 && subscriptionType <= 11) {
            color = context.getResources().getColor(R.color.theme_sub_gold);
        } else if (subscriptionType >= 12 && subscriptionType <= 24) {
            color = context.getResources().getColor(R.color.theme_sub_diamond);
        } else {
            color = context.getResources().getColor(R.color.theme_main);
        }

        holder.line.setBackgroundTintList(ColorStateList.valueOf(color));

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("TYPE", 1);
            bundle.putInt("USER_ID", DataSave.UserGetId(context));
            bundle.putInt("SUB_ID", subscription.getId());
            bundle.putLong("DISCOUNT", subscription.getDiscount());
            bundle.putLong("DISCOUNT_PERCENT", subscription.getDiscount_percent());
            bundle.putLong("PRICE_PURE", subscription.getPrice());
            bundle.putLong("PRICE_TAX", 0);
            bundle.putString("PAY_TOPIC", subscription.getName());
            bundle.putString("PAY_DESC", "فعال سازی حساب کاربری به حساب ویژه");

            Navigation.findNavController(view).navigate(R.id.action_subscriptionFragment_to_paymentFragment, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtPriod, txtDiscount, txtPrice, txtDiscountPercent;
        private View line;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPriod = itemView.findViewById(R.id.txtPriod);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtDiscountPercent = itemView.findViewById(R.id.txtDiscountPercent);
            txtDiscountPercent = itemView.findViewById(R.id.txtDiscountPercent);
            txtDiscountPercent = itemView.findViewById(R.id.txtDiscountPercent);
            line = itemView.findViewById(R.id.line);

        }
    }
}
