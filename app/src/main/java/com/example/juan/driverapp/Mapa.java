package com.example.juan.driverapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Mapa extends AppCompatActivity {
    private EditText entrada;
    private int[][] Mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada=(EditText)findViewById(R.id.editText);

        String REST_URI  = "http://192.168.100.4:8080/ServidorTEC/webapi/myresource/Mapa";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson=new Gson();
                        Mapa=gson.fromJson(response,int[][].class);
                        Toast.makeText(Mapa.this,
                                "Sent "+response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mapa.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(stringRequest);

    }


    public void SentGPS(View view){
        Toast.makeText(Mapa.this,
                "Sent "+entrada.getText(), Toast.LENGTH_LONG).show();

        String REST_URI  = "http://192.168.100.4:8080/ServidorTEC/webapi/myresource/Residencia";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson=new Gson();
                        Mapa=gson.fromJson(response,int[][].class);
                        Toast.makeText(Mapa.this,
                                "Sent "+response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mapa.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Residencia", ""+entrada.getText());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
