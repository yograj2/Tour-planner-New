package com.example.yogra.tourplanner.Util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.SiteInfo;

public class Calculation extends AppCompatActivity{

    public EditText night;
    public EditText nightcharges;
    public Button calculate_result;
    public TextView result;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        night = findViewById(R.id.number_of_night);
        nightcharges = findViewById(R.id.number_of_night_charges);
        calculate_result = findViewById(R.id.btn_number_night);
        result = findViewById(R.id.tv_night);

        calculate_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int num1 = Integer.parseInt(night.getText().toString());
                int num2 = Integer.parseInt(nightcharges.getText().toString());
                int mul = num1*num2;
                result.setText("price is :" +String.valueOf(mul));*/
                intent = new Intent(Calculation.this,SiteInfo.class);
                startActivity(intent);
              //  buttonPrice.setText("Price is :"+String.valueOf(mul));
            }
        });

    }
}
