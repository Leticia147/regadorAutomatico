package com.example.regador.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class AgendamentoResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dataInicial")
    @Expose
    private LocalDateTime dataInicial;
    @SerializedName("dataFinal")
    @Expose
    private LocalDateTime dataFinal;
    public AgendamentoResponse() {
    }

    public AgendamentoResponse(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.id = null;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }
    public AgendamentoResponse(String identificador, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.id = identificador;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}