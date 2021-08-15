package com.example.regador.http.model;

import java.time.LocalDateTime;

public class Agendamento {

    private String id;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;

    public Agendamento(LocalDateTime inicio, LocalDateTime fim, String id) {
        this.id = id;
        this.dataInicial = inicio;
        this.dataFinal = fim;
    }

    public Agendamento() {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
