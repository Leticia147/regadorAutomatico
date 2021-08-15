package com.example.regador.http;

public enum ServerEndpoints {

    AGENDAR("/regador/agendar"),
    AGENDAMENTOS("/regador/agendamentos"),
    EDITAR("/regador/agendar/"),
    DELETAR("/regador/agendamentos/"),
    LIGAR("/regador/ligar"),
    DESLIGAR("/regador/desligar");

    private static final String SERVER_ADDRESS = "http://192.168.0.173:8080";
    private final String endpoint;

    ServerEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUrl() {
        return SERVER_ADDRESS + endpoint;
    }

    public String getUrl(String id) {
        return SERVER_ADDRESS + endpoint + id;
    }


}
