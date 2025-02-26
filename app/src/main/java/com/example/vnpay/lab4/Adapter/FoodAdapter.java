package com.example.vnpay.lab4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnpay.R;
import com.example.vnpay.model.Food;

import java.util.HashSet;
import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private HashSet<Food> selectedFoods = new HashSet<>();

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodDescription.setText(food.getDescription());
        holder.tvFoodPrice.setText("Đơn giá: " + food.getPrice() + " VNĐ");
        holder.imgFood.setImageResource(food.getImage());

        holder.itemView.setBackgroundColor(selectedFoods.contains(food) ? 0xFFE0E0E0 : 0xFFFFFFFF);

        holder.itemView.setOnClickListener(v -> {
            if (selectedFoods.contains(food)) {
                selectedFoods.remove(food);
            } else {
                selectedFoods.add(food);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public HashSet<Food> getSelectedFoods() {
        return selectedFoods;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView tvFoodName, tvFoodDescription, tvFoodPrice;
        ImageView imgFood;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }
}
