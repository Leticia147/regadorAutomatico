package com.example.regador.ui.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.regador.R;
import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AcionarActivity extends AppCompatActivity {
    TextView textViewHoraFimImediato, textViewDataFimImediato;
    LocalDateTime fim = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acionar);

        textViewHoraFimImediato = findViewById(R.id.textViewHoraFimImediato);
        textViewDataFimImediato = findViewById(R.id.textViewDataFimImediato);

        TimePickerDialog timePickerDialogFim = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    fim = LocalDateTime.of(fim.getYear(), fim.getMonthValue(), fim.getDayOfMonth() , hourOfDay, minute);
                    textViewHoraFimImediato.setText(fim.getHour() + ":" + fim.getMinute() );
                },
                fim.getHour(),
                fim.getMinute(),
                true
        );


        DatePickerDialog datePickerDialogFim = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    fim = LocalDateTime.of(year, month+1, dayOfMonth, fim.getHour(), fim.getMinute());
                    textViewDataFimImediato.setText(fim.getDayOfMonth() + "/" + fim.getMonthValue()  + "/" + fim.getYear() );
                },
                fim.getYear(),
                fim.getMonthValue()-1,
                fim.getDayOfMonth()
        );



        textViewDataFimImediato.setOnClickListener(v -> datePickerDialogFim.show());
        textViewHoraFimImediato.setOnClickListener(v -> timePickerDialogFim.show());

        Button butttonLigar = findViewById(R.id.button_ligar2);

        butttonLigar.setOnClickListener(v -> startActivity(new Intent(AcionarActivity.this, MainActivity.class)));


    }
}

