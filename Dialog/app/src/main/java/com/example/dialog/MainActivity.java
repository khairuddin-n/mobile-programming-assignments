package com.example.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView hasilnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasilnya = findViewById(R.id.result);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(op);
    }

    View.OnClickListener op = v -> {
        if (v.getId() == R.id.button) {
            showInputDialog();
        }
    };

    private void showInputDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.input_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        EditText inputText = dialogView.findViewById(R.id.edittext);

        dialogBuilder.setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    String inputValue = inputText.getText().toString();
                    hasilnya.setText(inputValue); // Set the input value to hasilnya TextView
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}