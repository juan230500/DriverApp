package com.example.juan.driverapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
private EditText entrada;
private int[][] Mapa;
private String Matriz="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada=(EditText)findViewById(R.id.editText);

        String REST_URI  = "http://192.168.100.22:8080/ServidorTEC/webapi/myresource/Mapa";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson=new Gson();
                        Mapa=gson.fromJson(response,int[][].class);
                        Matriz=response;
                        Toast.makeText(MainActivity.this,
                                "Sent hola "+response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(stringRequest);

    }
    /*
    1.Registrar residencia
    2.Toast con caminos por el mapa
    3.arrays con xy de los nodos
    4.Bitacora XD
     */
    public void giveMeConexiones(View view){
        int  response=Mapa[1][1];
        if (!entrada.getText().equals("")){
            String valor=entrada.getText().toString();
            int nodo_a_Verificar=Integer.parseInt(valor);
            String resultado="";
            int Fila=30;
            while (Fila!=0){
                if (Mapa[Fila][nodo_a_Verificar]!=0){
                    resultado+="=>"+Fila+"("+Mapa[Fila][nodo_a_Verificar]+")\n";
                }
                Fila-=1;
            }
            Toast.makeText(MainActivity.this,
                    resultado, Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(MainActivity.this,
                    "Porfa llene el espacio de texto", Toast.LENGTH_LONG).show();
        }
    }

    public void SentGPS(View view){
        Toast.makeText(MainActivity.this,
                "Sent "+entrada.getText(), Toast.LENGTH_LONG).show();

        String REST_URI  = "http://192.168.100.22:8080/ServidorTEC/webapi/myresource/Residencia";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson=new Gson();
                        Mapa=gson.fromJson(response,int[][].class);
                        Toast.makeText(MainActivity.this,
                                "Sent "+response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Residencia", ""+entrada.getText());
                params.put("Carne","2018135360");

                return params;
        }
        };

        requestQueue.add(stringRequest);
    }

}
