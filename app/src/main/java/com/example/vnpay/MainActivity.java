package com.example.vnpay;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vnpay.lab4.DrinkActivity;
import com.example.vnpay.lab4.FoodActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textViewSelectedFood;
    private TextView textViewSelectedDrink;

    private Button buttonTotalCost;
    private Button buttonRefresh;

    private ActivityResultLauncher<Intent> foodActivityResultLauncher;
    private ActivityResultLauncher<Intent> drinkActivityResultLauncher;
    private int totalCost = 0;
    private int totalFoodCost = 0;
    private int totalDrinkCost = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewSelectedFood = findViewById(R.id.tvSelectedFood);
        textViewSelectedDrink = findViewById(R.id.tvSelectedDrink);
        buttonTotalCost = findViewById(R.id.totalCost);
        buttonRefresh = findViewById(R.id.refresh);

        Button buttonChooseFood = findViewById(R.id.btn_choose_food);
        Button buttonChooseDrink = findViewById(R.id.btn_choose_drink);
        buttonTotalCost.setText("Check Out: "+totalCost+" VNĐ");


        buttonTotalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCost<=0){
                    Toast.makeText(MainActivity.this, "Please choose food and drink", Toast.LENGTH_SHORT).show();
                }else
                    CheckOut(totalCost);
            }
        });
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCost <= 0) {
                    Toast.makeText(MainActivity.this, "Please choose food and drink", Toast.LENGTH_SHORT).show();
                } else {
                    recreate();
                }
            }
        });

        // Register the ActivityResultLauncher for FoodActivity
        foodActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    totalFoodCost = 0;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        ArrayList<String> selectedFoodNames = data.getStringArrayListExtra("selected_food_names");
                        ArrayList<Integer> selectedFoodPrices = data.getIntegerArrayListExtra("selected_food_prices");

                        StringBuilder foodSummary = new StringBuilder("Foods:\n");
                        if (selectedFoodNames != null) {
                            for (int i = 0; i < selectedFoodNames.size(); i++) {
                                foodSummary.append(selectedFoodNames.get(i))
                                        .append(" - ")
                                        .append(selectedFoodPrices.get(i))
                                        .append(" VNĐ\n");
                                // add cost to total food cost
                                totalFoodCost += selectedFoodPrices.get(i);
                            }
                        }
                        textViewSelectedFood.setText(foodSummary.toString());
                        CalTotal();
                    }
                }
        );

        // Register the ActivityResultLauncher for DrinkActivity
        drinkActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    totalDrinkCost = 0;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        ArrayList<String> selectedDrinkNames = data.getStringArrayListExtra("selected_drink_names");
                        ArrayList<Integer> selectedDrinkPrices = data.getIntegerArrayListExtra("selected_drink_prices");

                        StringBuilder drinkSummary = new StringBuilder("Drinks:\n");
                        if (selectedDrinkNames != null) {
                            for (int i = 0; i < selectedDrinkNames.size(); i++) {
                                drinkSummary.append(selectedDrinkNames.get(i))
                                        .append(" - ")
                                        .append(selectedDrinkPrices.get(i))
                                        .append(" VNĐ\n");
                                totalDrinkCost += selectedDrinkPrices.get(i);
                            }
                        }
                        textViewSelectedDrink.setText(drinkSummary.toString());
                        CalTotal();
                    }
                }
        );

        // Set onClickListeners to launch the activities
        buttonChooseFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            foodActivityResultLauncher.launch(intent);
        });

        buttonChooseDrink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            drinkActivityResultLauncher.launch(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if (uri != null) {
            String responseCode = uri.getQueryParameter("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Thanh toán thất bại hoặc bị hủy!", Toast.LENGTH_LONG).show();
            }
        }
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Uri data = intent.getData();
//        if (data != null && "myapp".equals(data.getScheme())) {
//            // Xử lý kết quả từ VNPay
//            Toast.makeText(this, "Thanh toán hoàn tất!", Toast.LENGTH_SHORT).show();
//        }
//    }


    //==============================================================
    protected void CalTotal() {
        totalCost = totalFoodCost + totalDrinkCost;
        buttonTotalCost = findViewById(R.id.totalCost);
        buttonTotalCost.setText("Check Out: "+totalCost+" VNĐ");
    }

    protected void CheckOut(int totalCost){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Checkout?")
                .setMessage("Payment for food and drinks: " + totalCost)
                .setPositiveButton("Yes", (dialog, which) -> {

                    Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show();

                    //QUAN TRỌNG
                    String orderId = String.valueOf(System.currentTimeMillis());
                    String paymentUrl = null;

                    try {
                        paymentUrl = VNPay.getPaymentUrl(orderId, totalCost);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
                    startActivity(intent);
                    //QUAN TRỌNG

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()) // Đóng dialog nếu hủy
                .show();
    }


}