package com.example.regador.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.regador.R;
import com.example.regador.http.LocalDateTimeAdapter;
import com.example.regador.http.ServerEndpoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AgendamentosActivity extends AppCompatActivity  {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    List<Agendamento> agendamentosList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos);
        getAgendamentosRequest();

    }

    public void getAgendamentosRequest() {
        Volley.newRequestQueue(this).add(new JsonArrayRequest(
                        Request.Method.GET,
                        ServerEndpoints.AGENDAMENTOS.getUrl(),
                        null,
                        response -> {
                            try {
                                agendamentosList = buildFromResponse(response);
                                preencherListView(agendamentosList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()
                )
        );
    }

    public List<Agendamento> buildFromResponse(JSONArray response) throws JSONException {
        List<Agendamento> agendamentos = new ArrayList<>(response.length());
        for (int i = 0;i<response.length(); i++){
            JSONObject jsonObject = (JSONObject) response.get(i);
           agendamentos.add(gson.fromJson(jsonObject.toString(), Agendamento.class));
        }
        return agendamentos;
    }

    public void preencherListView(List<Agendamento> agendamentos){
        ListView listAgendamentos = (ListView) findViewById(R.id.listViewAgendamentos);

        String[] arrayAgendamentosDataFinal = new String[agendamentos.size()];
        String[] arrayAgendamentosDataInicial = new String[agendamentos.size()];

        for (int i = 0; i < agendamentos.size(); i++) {
            arrayAgendamentosDataInicial[i] = agendamentos.get(i).getDataInicial().toString();
            arrayAgendamentosDataFinal[i] = agendamentos.get(i).getDataFinal().toString();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayAgendamentosDataInicial);

        adapter = new MyAdapter(this,arrayAgendamentosDataInicial,arrayAgendamentosDataFinal );
        listAgendamentos.setAdapter(adapter);

        registerForContextMenu(listAgendamentos);
    }
    
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String horaInicio[];
        String horaFim[];

        MyAdapter(Context c, String horaInicio[],String horaFim[]){
            super(c, R.layout.row, R.id.textViewDataHoraInicio,horaInicio);
            this.context = c;
            this.horaInicio = horaInicio;
            this.horaFim = horaFim;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView dataHoraInicio = row.findViewById(R.id.textViewDataHoraInicio);
            TextView dataHoraFim = row.findViewById(R.id.extViewDataHoraFim);

            dataHoraInicio.setText(horaInicio[position]);
            dataHoraFim.setText(horaFim[position]);

            return row;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewAgendamentos) {
            menu.add("Excluir");
            menu.add("Editar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if ("Excluir".equals(item.toString())) {
            // Toast.makeText(getApplicationContext(), "Agendamento removido.", Toast.LENGTH_LONG).show();
            setRemoverRequest(agendamentosList.get(info.position).getId());
            finish();
            startActivity(new Intent(AgendamentosActivity.this, AgendamentosActivity.class));
        }
        else if ("Editar".equals(item.toString())) {
            Toast.makeText(getApplicationContext(), "PARA EDITAR.", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void setRemoverRequest(String id) {

        Volley.newRequestQueue(this).add(new JsonObjectRequest(
                        Request.Method.DELETE,
                        ServerEndpoints.DELETAR.getUrl(id),
                null,
                        response -> Toast.makeText(getApplicationContext(), "Agendamento removido.", Toast.LENGTH_SHORT).show(),
                        error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()
                )
        );
    }
}
