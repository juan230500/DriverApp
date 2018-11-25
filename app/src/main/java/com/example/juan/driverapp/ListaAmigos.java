package com.example.juan.driverapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaAmigos extends AppCompatActivity {
    private LinearLayout contenedor;
    private String carne = "2018135360";
    private String ip = "192.168.100.12";
    private ArrayList<String> carnes ;
    private ArrayList<String> calif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);
        this.contenedor = (LinearLayout)findViewById(R.id.lloCont);
        carnes = new ArrayList<String>();
        calif = new ArrayList<String>();
        getLista();



    }
    public void mostrarAmigos (){
        int index = 0;
        for (String b: carnes){
            Double num1 = (Double.parseDouble(calif.get(index))) * 0.01;
            TextView txt = new TextView(this);
            txt.setText(b + "\n" + num1.toString());
            txt.setBackgroundColor(Color.parseColor("#e1e1e1"));
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setTextSize(26);
            contenedor.addView(txt);
            index ++;
        }
    }
    public void getLista (){

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String REST_URI  = "http://" + ip + ":8080/ServidorTEC/webapi/myresource/Amigos";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ListaAmigos.this, "Response " + response, Toast.LENGTH_LONG).show();



                        parsear(response);
                        mostrarAmigos();

                        //Toast.makeText(ListaAmigos.this, jobject.toString(), Toast.LENGTH_LONG).show();
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
    public void parsear(String response){
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);


        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();

            JsonArray amigs = details.getAsJsonArray("Amigos");

            for (int i = 0; i < amigs.size(); i++) {
                JsonPrimitive value = amigs.get(i).getAsJsonPrimitive();
                carnes.add(value.getAsString());
            }

            JsonArray calificaciones = details.getAsJsonArray("Calificaciones");

            for (int i = 0; i < calificaciones.size(); i++) {
                JsonPrimitive value = calificaciones.get(i).getAsJsonPrimitive();
                calif.add(value.getAsString());
                //Toast.makeText(ListaAmigos.this, value.getAsString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
