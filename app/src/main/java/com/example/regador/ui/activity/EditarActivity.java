package com.example.regador.ui.activity;

import static com.example.regador.ui.activity.AgendamentosActivity.gson;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.example.regador.http.model.Agendamento;
import com.example.regador.http.model.AgendamentoRequest;
import com.example.regador.http.model.ApiError;

import org.json.JSONException;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LocalDateTime inicio;
    LocalDateTime fim;
    TextView textViewDataInicio, textViewHoraInicio, textViewDataFim, textViewHoraFim;
    Button buttonEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);

        String idEdit = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idEdit = extras.getString("key");
            getAgendamentoRequest(idEdit);
        }

    }

    public void getAgendamentoRequest(String id)  {
        Volley.newRequestQueue(this).add(new JsonObjectRequest(
                        Request.Method.GET,
                        ServerEndpoints.AGENDAMENTO.getUrl(id),
                        null,
                        response -> {
                            Agendamento agendamento = gson.fromJson(response.toString(), Agendamento.class);
                            inicio = agendamento.getDataInicial();
                            fim = agendamento.getDataFinal();

                            buttonEditar = findViewById(R.id.button_Agendar_Fim);
                            buttonEditar.setText("Confirmar");

                            textViewDataInicio = findViewById(R.id.textViewDataInicio);
                            textViewHoraInicio = findViewById(R.id.textViewHoraInicio);
                            textViewDataFim = findViewById(R.id.textViewDataFim);
                            textViewHoraFim = findViewById(R.id.textViewHoraFim);

                            textViewDataInicio.setText(inicio.getDayOfMonth() + "/" + inicio.getMonthValue() + "/" + inicio.getYear());
                            textViewDataFim.setText(fim.getDayOfMonth() + "/" + fim.getMonthValue() + "/" + fim.getYear());
                            textViewHoraInicio.setText(inicio.getHour() + ":" + inicio.getMinute());
                            textViewHoraFim.setText(fim.getHour() + ":" + fim.getMinute());

                            DatePickerDialog datePickerDialogInicio = new DatePickerDialog(
                                    this,
                                    (view, year, month, dayOfMonth) -> {
                                        inicio = LocalDateTime.of(year, month + 1, dayOfMonth, inicio.getHour(), inicio.getMinute());
                                        textViewDataInicio.setText(inicio.getDayOfMonth() + "/" + inicio.getMonthValue() + "/" + inicio.getYear());
                                    },
                                    fim.getYear(),
                                    fim.getMonthValue()-1,
                                    fim.getDayOfMonth()
                            );

                            DatePickerDialog datePickerDialogFim = new DatePickerDialog(
                                    this,
                                    (view, year, month, dayOfMonth) -> {
                                        fim = LocalDateTime.of(year, month + 1, dayOfMonth, inicio.getHour(), inicio.getMinute());
                                        textViewDataFim.setText(fim.getDayOfMonth() + "/" + fim.getMonthValue() + "/" + fim.getYear());
                                    },
                                    fim.getYear(),
                                    fim.getMonthValue()-1,
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

                            textViewDataInicio.setOnClickListener(v -> datePickerDialogInicio.show());
                            textViewHoraInicio.setOnClickListener(v -> timePickerDialogInicio.show());
                            textViewDataFim.setOnClickListener(v -> datePickerDialogFim.show());
                            textViewHoraFim.setOnClickListener(v -> timePickerDialogFim.show());
                            String finalIdEdit = id;

                            buttonEditar.setOnClickListener(view -> {
                                sendEditaAgendamentoRequest(new AgendamentoRequest(inicio, fim), finalIdEdit);
                                startActivity(new Intent(EditarActivity.this, MainActivity.class));
                            });


                        },
                        error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()
                )
        );
    }

    public void sendEditaAgendamentoRequest(AgendamentoRequest agendamentoRequest, String id) {
        try {
            Volley.newRequestQueue(this).add(
                    new JsonObjectRequest(
                            Request.Method.PUT,
                            ServerEndpoints.EDITAR.getUrl(id),
                            agendamentoRequest.toJSONObject(),
                            response -> {
                                Toast.makeText(getApplicationContext(), "Agendamento editado com sucesso!", Toast.LENGTH_SHORT).show();
                            },
                            error -> {
                                ApiError apiError = gson.fromJson(new String(error.networkResponse.data), ApiError.class);

                                Toast.makeText(getApplicationContext(), "Erro ao editar. Motivo: " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    )
            );
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Erro ao editar. Motivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

}
