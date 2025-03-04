package com.example.vnpay.lab4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnpay.MainActivity;
import com.example.vnpay.R;
import com.example.vnpay.lab4.Adapter.FoodAdapter;
import com.example.vnpay.model.Food;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FoodActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        recyclerViewFood = findViewById(R.id.recyclerViewFoods);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();
        foodList.add(new Food("Cánh gà chiên nước mắm", "Vị giác bùng nổ", R.drawable.canh_ga_chien_nuoc_mam, 120000));
        foodList.add(new Food("Heo quay", "Giòn đến miếng cuối cùng", R.drawable.heo_quay, 220000));
        foodList.add(new Food("Rau muống xào tỏi", "Hương vị của miền quê", R.drawable.rau_muong_xao_toi, 60000));
        foodList.add(new Food("Vịt rang muối", "Muối ngon hơn vịt", R.drawable.vit_rang_muoi, 115000));
        foodList.add(new Food("Phở Hà Nội", "Hà Nội không vội được đâu", R.drawable.pho_bo, 30000));
        foodList.add(new Food("Bún Bò Huế", "Chuẩn vị Huế", R.drawable.bun_bo_hue, 35000));

        foodAdapter = new FoodAdapter(foodList);
        recyclerViewFood.setAdapter(foodAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Food> selectedFoods = foodAdapter.getSelectedFoods();
                ArrayList<String> selectedFoodNames = new ArrayList<>();
                ArrayList<Integer> selectedFoodPrices = new ArrayList<>();
                // Chuyển đổi HashSet thành ArrayList
                for (Food food : selectedFoods) {
                    selectedFoodNames.add(food.getName());
                    selectedFoodPrices.add(food.getPrice());
                }
                Intent intent = new Intent(FoodActivity.this, MainActivity.class);
                intent.putStringArrayListExtra("selected_food_names", selectedFoodNames);
                intent.putIntegerArrayListExtra("selected_food_prices", selectedFoodPrices);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}