package com.example.regador.http.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgendamentoRequest {
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;

    public AgendamentoRequest(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public AgendamentoRequest() {
    }

    public LocalDateTime getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataInicial", this.dataInicial.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        jsonObject.put("dataFinal", this.dataFinal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        return jsonObject;
    }

}