package com.example.vnpay.lab4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnpay.R;
import com.example.vnpay.model.Drink;

import java.util.HashSet;
import java.util.List;


public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private List<Drink> drinkList;
    private HashSet<Drink> selectedDrinks = new HashSet<>();
    private OnDrinkClickListener listener;

    public DrinkAdapter(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_item, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinkList.get(position);
        holder.tvDrinkName.setText(drink.getName());
        holder.tvDrinkDescription.setText(drink.getDescription());
        holder.tvDrinkPrice.setText("Đơn giá: " + drink.getPrice() + " VNĐ");
        holder.imgDrink.setImageResource(drink.getImage());

        holder.itemView.setBackgroundColor(selectedDrinks.contains(drink) ? 0xFFE0E0E0 : 0xFFFFFFFF);

        holder.itemView.setOnClickListener(v -> {
            if (selectedDrinks.contains(drink)) {
                selectedDrinks.remove(drink);
            } else {
                selectedDrinks.add(drink);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public HashSet<Drink> getSelectedDrinks() {
        return selectedDrinks;
    }

    public interface OnDrinkClickListener {
        void onDrinkClick(Drink drink);
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrinkName, tvDrinkDescription, tvDrinkPrice;
        ImageView imgDrink;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrinkName = itemView.findViewById(R.id.tvDrinkName);
            tvDrinkDescription = itemView.findViewById(R.id.tvDrinkDescription);
            tvDrinkPrice = itemView.findViewById(R.id.tvDrinkPrice);
            imgDrink = itemView.findViewById(R.id.imgDrink);
        }
    }
}
