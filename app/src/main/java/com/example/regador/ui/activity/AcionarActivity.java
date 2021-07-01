package com.example.regador.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.regador.R;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mqtt.Mqtt;

public class AcionarActivity extends AppCompatActivity{
    private Button button_ligar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acionar);

        button_ligar2 = findViewById(R.id.button_ligar2);

        TextView segundosV = findViewById(R.id.editTextNumberDecimal2);

        Mqtt.mqttPublish(77);

        button_ligar2.setOnClickListener(v -> startActivity(new Intent(AcionarActivity.this, MainActivity.class)));

    }
}

