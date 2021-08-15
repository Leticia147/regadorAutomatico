package com.example.regador.ui.activity;


import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.regador.R;
import com.example.regador.http.ServerEndpoints;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AcionarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acionar);
        Button butttonLigar = findViewById(R.id.button_acionarRegador);
        Button butttonDesliigar = findViewById(R.id.button_desligarRegador);

        butttonLigar.setOnClickListener(v -> sendLigar());
        butttonDesliigar.setOnClickListener(v -> sendDesligar());
    }

    public void sendLigar() {
        try {
            Volley.newRequestQueue(this).add(
                    new JsonObjectRequest(
                            Request.Method.POST,
                            ServerEndpoints.LIGAR.getUrl(),
                            null,
                            response -> Toast.makeText(getApplicationContext(), "Regador ligado com sucesso!", Toast.LENGTH_LONG).show(),
                            error -> Toast.makeText(getApplicationContext(), "Erro ao ligar. Motivo: " + error.getMessage(), Toast.LENGTH_LONG).show()
                    )
            );
        } catch (Exception e) {

        }
    }

    public void sendDesligar() {
        try {
            Volley.newRequestQueue(this).add(
                    new JsonObjectRequest(
                            Request.Method.POST,
                            ServerEndpoints.DESLIGAR.getUrl(),
                            null,
                            response -> Toast.makeText(getApplicationContext(), "Regador desligado com sucesso!", Toast.LENGTH_LONG).show(),
                            error -> Toast.makeText(getApplicationContext(), "Erro ao desligar. Motivo: " + error.getMessage(), Toast.LENGTH_LONG).show()
                    )
            );
        } catch (Exception e) {

        }
    }

}

