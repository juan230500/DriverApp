package com.example.juan.driverapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase corresponde a la pantalla que muestra los amigos agregados previamente junto a su calificación
 */
public class ListaAmigos extends AppCompatActivity {
    private LinearLayout contenedor;
    private String carne;
    private String ip = "192.168.100.12";
    private String[] carnes ;
    private String[] calif;
    private TextView tv1;
    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);
        tv1 = (TextView)findViewById(R.id.tv1);
        lv1 = (ListView)findViewById(R.id.lv1);
        getLista();
        abrir();
    }

    /**
     * Este método muestra en pantalla todos los amigos agregados junto a sus calificaciones promedio
     */
    public void mostrarAmigos (){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_amigos, carnes);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double Calificacion=Double.parseDouble(calif[i])/100;
                tv1.setText("La calificion de " + lv1.getItemAtPosition(i) + " es " + Calificacion);
            }
        });

    }

    /**
     * Este método abre el archivo que contiene el carné del conductor registrado y lo guarda en un atributo "carne"
     */
    public void abrir(){
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("micarne.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            carne = br.readLine();
        } catch (IOException e){}
    }

    /**
     * Este método se encarga de enviar la peticion de la lista de los amigos al servidor
     */
    public void getLista (){

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String REST_URI  = "http://" + ip + ":8080/ServidorTEC/webapi/myresource/Amigos";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        parsear(response);
                        mostrarAmigos();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListaAmigos.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Carne", "" + carne);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    /**
     * Este método hace parse a las respuestas obtenidas del servidor
     * @param response corresponde al String en JSON que se desea parsear
     */
    public void parsear(String response){
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);


        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();

            JsonArray amigs = details.getAsJsonArray("Amigos");

            carnes=new String[amigs.size()];
            calif=new String[amigs.size()];

            for (int i = 0; i < amigs.size(); i++) {
                JsonPrimitive value = amigs.get(i).getAsJsonPrimitive();
                carnes[i]=value.getAsString();
            }

            JsonArray calificaciones = details.getAsJsonArray("Calificaciones");

            for (int i = 0; i < calificaciones.size(); i++) {
                JsonPrimitive value = calificaciones.get(i).getAsJsonPrimitive();
                calif[i]=value.getAsString();
                //Toast.makeText(ListaAmigos.this, value.getAsString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
