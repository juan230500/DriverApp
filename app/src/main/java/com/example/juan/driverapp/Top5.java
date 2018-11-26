package com.example.juan.driverapp;

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
import com.google.gson.Gson;

import java.util.Arrays;

public class Top5 extends AppCompatActivity {
    private LinearLayout contenedor;
    private String ip = "192.168.100.12";
    private String [] carnes;
    private String [] calif;

    private TextView tv1;
    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5);
        tv1 = (TextView)findViewById(R.id.tv1);
        lv1 = (ListView)findViewById(R.id.lv1);

        getLista();
    }
    public void mostrarAmigos (){
        tv1 = (TextView)findViewById(R.id.tv1);
        lv1 = (ListView)findViewById(R.id.lv1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_amigos, carnes);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv1.setText("Los viajes totales de " + lv1.getItemAtPosition(i) + " son " + calif[i]);
            }
        });
    }
    public void getLista (){

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String REST_URI  = "http://" + ip + ":8080/ServidorTEC/webapi/myresource/Top5";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REST_URI,
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
                        Toast.makeText(Top5.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    public void parsear(String response){
        Gson gson=new Gson();
        String [][] S = gson.fromJson(response, String [][].class);
        carnes = S[0];
        calif = S[1];
    }
}
