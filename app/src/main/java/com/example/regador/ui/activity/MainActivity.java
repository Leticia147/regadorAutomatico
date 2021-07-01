package com.example.regador.ui.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.regador.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonAgendar;
    private Button buttonAcionar;
    private Button buttonAgendamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAgendar = findViewById(R.id.button_Agendar);
        buttonAcionar = findViewById(R.id.button_Acionar);
        buttonAgendamentos = findViewById(R.id.button_Agendamentos);

        buttonAgendar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AgendarActivity.class)));
        buttonAcionar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AcionarActivity.class)));
        buttonAgendamentos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AgendamentosActivity.class)));
    }
}