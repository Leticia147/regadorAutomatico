package com.example.regador.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.regador.R;
import com.example.regador.http.ServerEndpoints;
import com.example.regador.http.model.AgendamentoRequest;

import org.json.JSONException;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AgendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LocalDateTime inicio = LocalDateTime.now();
    LocalDateTime fim = LocalDateTime.now();
    TextView textViewDataInicio, textViewHoraInicio, textViewDataFim, textViewHoraFim;
    Button buttonAgendarFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        DatePickerDialog datePickerDialogInicio = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    inicio = LocalDateTime.of(year, month + 1, dayOfMonth, inicio.getHour(), inicio.getMinute());
                    textViewDataInicio.setText(inicio.getDayOfMonth() + "/" + inicio.getMonthValue() + "/" + inicio.getYear());
                },
                inicio.getYear(),
                inicio.getMonthValue() - 1,
                inicio.getDayOfMonth()
        );

        DatePickerDialog datePickerDialogFim = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    fim = LocalDateTime.of(year, month + 1, dayOfMonth, fim.getHour(), fim.getMinute());
                    textViewDataFim.setText(fim.getDayOfMonth() + "/" + fim.getMonthValue() + "/" + fim.getYear());
                },
                fim.getYear(),
                fim.getMonthValue() - 1,
                fim.getDayOfMonth()
        );

        TimePickerDialog timePickerDialogInicio = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    inicio = LocalDateTime.of(inicio.getYear(), inicio.getMonthValue(), inicio.getDayOfMonth(), hourOfDay, minute);
                    textViewHoraInicio.setText(inicio.getHour() + ":" + inicio.getMinute());
                },
                inicio.getHour(),
                inicio.getMinute(),
                true
        );


        TimePickerDialog timePickerDialogFim = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    fim = LocalDateTime.of(fim.getYear(), fim.getMonthValue(), fim.getDayOfMonth(), hourOfDay, minute);
                    textViewHoraFim.setText(fim.getHour() + ":" + fim.getMinute());
                },
                fim.getHour(),
                fim.getMinute(),
                true
        );


        buttonAgendarFim = findViewById(R.id.button_Agendar_Fim);
        textViewDataInicio = findViewById(R.id.textViewDataInicio);
        textViewHoraInicio = findViewById(R.id.textViewHoraInicio);
        textViewDataFim = findViewById(R.id.textViewDataFim);
        textViewHoraFim = findViewById(R.id.textViewHoraFim);

        textViewDataInicio.setOnClickListener(v -> datePickerDialogInicio.show());
        textViewHoraInicio.setOnClickListener(v -> timePickerDialogInicio.show());
        textViewDataFim.setOnClickListener(v -> datePickerDialogFim.show());
        textViewHoraFim.setOnClickListener(v -> timePickerDialogFim.show());
        buttonAgendarFim.setOnClickListener(view -> sendAgendamentoRequest(new AgendamentoRequest(inicio, fim)));
    }


    public void sendAgendamentoRequest(AgendamentoRequest agendamentoRequest) {
        try {
            Volley.newRequestQueue(this).add(
                    new JsonObjectRequest(
                            Request.Method.POST,
                            ServerEndpoints.AGENDAR.getUrl(),
                            agendamentoRequest.toJSONObject(),
                            response -> Toast.makeText(getApplicationContext(), "Agendamento realizado com sucesso!", Toast.LENGTH_SHORT).show(),
                            error -> Toast.makeText(getApplicationContext(), "Erro ao agendar. Motivo: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                    )
            );
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Erro ao agendar. Motivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

}
