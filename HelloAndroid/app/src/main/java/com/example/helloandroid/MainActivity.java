package com.example.helloandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_text);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            textView.setText("Hello, " + inputText + "!");
        });
    }
}