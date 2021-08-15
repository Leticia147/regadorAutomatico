package com.example.regador.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regador.R;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    int tHour, tMinute;
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

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //LocalDateTime.of();
        showTimePickerDialoger();
        String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();

    }

    public void showTimePickerDialoger() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                MainActivity.this,
                (view, hourOfday, minute) -> {
                    tHour = hourOfday;
                    tMinute = minute;
                },
                12,
                0,
                false
        );
        timePickerDialog.updateTime(tHour, tMinute);
        timePickerDialog.show();
        Toast.makeText(getApplicationContext(), tHour + ":" + tMinute, Toast.LENGTH_SHORT).show();
    }
}