package com.example.kalkulatorsederhana;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private StringBuilder currentNumber;
    private double operand1, operand2;
    private char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        currentNumber = new StringBuilder();

        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            currentNumber.append(button.getText().toString());
            textViewResult.setText(currentNumber.toString());
        };

        View.OnClickListener operatorClickListener = v -> {
            if (currentNumber.length() > 0) {
                operand1 = Double.parseDouble(currentNumber.toString());
                currentNumber.setLength(0);
            }
            operator = ((Button) v).getText().charAt(0);
        };

        findViewById(R.id.buttonEquals).setOnClickListener(v -> {
            if (currentNumber.length() > 0) {
                operand2 = Double.parseDouble(currentNumber.toString());
                double result = calculate(operand1, operator, operand2);
                textViewResult.setText(String.valueOf(result));
                currentNumber.setLength(0);
                currentNumber.append(result);
            }
        });

        // Assign listeners for number buttons
        for (int i = 0; i <= 9; i++) {
            @SuppressLint("DiscouragedApi") int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            findViewById(id).setOnClickListener(numberClickListener);
        }

        // Assign listeners for operator buttons
        findViewById(R.id.buttonPlus).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonMinus).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonMultiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonDivide).setOnClickListener(operatorClickListener);

        // Assign listener for Clear button
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            currentNumber.setLength(0);
            textViewResult.setText("");
        });

        // Assign listener for Reset button
        findViewById(R.id.buttonReset).setOnClickListener(v -> {
            currentNumber.setLength(0);
            operand1 = 0;
            operand2 = 0;
            operator = ' ';
            textViewResult.setText("");
        });
    }

    private double calculate(double num1, char operator, double num2) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 != 0)
                    return num1 / num2;
                else
                    return Double.NaN; // Handling division by zero
            default:
                return Double.NaN;
        }
    }
}

