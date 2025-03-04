package com.example.vnpay.lab4;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vnpay.R;
import com.example.vnpay.lab4.Adapter.DrinkAdapter;
import com.example.vnpay.model.Drink;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class DrinkActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDrinks;
    private DrinkAdapter drinkAdapter;
    private List<Drink> drinkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        recyclerViewDrinks = findViewById(R.id.recyclerViewDrinks);
        recyclerViewDrinks.setLayoutManager(new LinearLayoutManager(this));

        drinkList = new ArrayList<>();
        drinkList.add(new Drink("Heineken", "Không say không về", R.drawable.heineken, 50000));
        drinkList.add(new Drink("Pepsi", "Đã quá pepsi ơi", R.drawable.pepsi, 20000));
        drinkList.add(new Drink("Bia Sài Gòn", "Sài gòn hoa lệ", R.drawable.saigon, 30000));
        drinkList.add(new Drink("Bia Tiger", "Chúa tể rừng xanh", R.drawable.biatiger, 35000));
        drinkList.add(new Drink("Cocacola", "Cô ca cô la", R.drawable.coca, 15000));
        drinkList.add(new Drink("Bia 333", "Uống xong la", R.drawable.bia333, 18000));

        drinkAdapter = new DrinkAdapter(drinkList);
        recyclerViewDrinks.setAdapter(drinkAdapter);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Drink> selectedDrinks = drinkAdapter.getSelectedDrinks();
                ArrayList<String> drinkNames = new ArrayList<>();
                ArrayList<Integer> drinkPrices = new ArrayList<>();

                for (Drink drink : selectedDrinks) {
                    drinkNames.add(drink.getName());
                    drinkPrices.add(drink.getPrice());
                }
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("selected_drink_names", drinkNames);
                resultIntent.putIntegerArrayListExtra("selected_drink_prices", drinkPrices);
                setResult(RESULT_OK, resultIntent);
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

    private void initializeDrinkList() {

    }
}