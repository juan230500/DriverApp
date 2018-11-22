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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        );

        requestQueue.add(stringRequest);

    }


    public void SentGPS(View view){
        Toast.makeText(MainActivity.this,
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

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
