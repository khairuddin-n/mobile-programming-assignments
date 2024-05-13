package com.example.hitungluas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLength;
    private EditText editTextWidth;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLength = findViewById(R.id.editTextLength);
        editTextWidth = findViewById(R.id.editTextWidth);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        buttonCalculate.setOnClickListener(v -> calculateArea());
    }

    @SuppressLint("SetTextI18n")
    private void calculateArea() {
        double length = Double.parseDouble(editTextLength.getText().toString());
        double width = Double.parseDouble(editTextWidth.getText().toString());

        double area = length * width;

        textViewResult.setText("Hasil Luas: " + area);
    }
}
