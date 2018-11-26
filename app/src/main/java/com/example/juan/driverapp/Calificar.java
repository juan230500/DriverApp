package com.example.juan.driverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calificar extends AppCompatActivity {

    private ArrayList<String> Pasajeros;
    private RatingBar calificacion;
    private boolean califico = false;
    private String carne;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);
        abrirPasajeros();
        calificacion = (RatingBar) findViewById(R.id.ratingBar);
    }


    public void enviarCalificacion(View view) {
        if (!califico) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "la puntuacion a enviar es de  " + calificacion.getRating(), Toast.LENGTH_SHORT);
            toast1.show();
            i = Pasajeros.size();
            enviar();
        } else {
            Toast.makeText(this, "Ya se ha calificado el viaje", Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void regresar(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void enviar() {
        i--;
        if (i<0)
            return;
        String REST_URI = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/Calificar";


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        enviar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Calificar.this,
                                "Sent " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Carne", "" + Pasajeros.get(i).substring(1));
                params.put("Calificacion", "" + calificacion.getRating());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void abrirPasajeros(){
        Pasajeros = new ArrayList<String>();
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("mispasajeros.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            String linea = br.readLine();
            while (linea != null) {
                Pasajeros.add(linea);
                linea = br.readLine();
            }
        } catch (IOException e){}
    }
}
