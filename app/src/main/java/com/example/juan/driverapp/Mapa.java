package com.example.juan.driverapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private Button Button0;
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;
    private Button Button6;
    private Button Button7;
    private Button Button8;
    private Button Button9;
    private Button Button10;
    private Button Button11;
    private Button Button12;
    private Button Button13;
    private Button Button14;
    private Button Button15;
    private Button Button16;
    private Button Button17;
    private Button Button18;
    private Button Button19;
    private Button Button20;
    private Button Button21;
    private Button Button22;
    private Button Button23;
    private Button Button24;
    private Button Button25;
    private Button Button26;
    private Button Button27;
    private Button Button28;
    private Button Button29;
    private Button Button30;


    private int[][] Mapa;
    private String Matriz="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa);
        entrada=(EditText)findViewById(R.id.editText);
        /*Button0=(Button)findViewById(R.id.button0);
        Button1=(Button)findViewById(R.id.button1);
        Button2=(Button)findViewById(R.id.button2);
        Button3=(Button)findViewById(R.id.button3);
        Button4=(Button)findViewById(R.id.button4);
        Button5=(Button)findViewById(R.id.button5);
        Button6=(Button)findViewById(R.id.button6);
        Button7=(Button)findViewById(R.id.button7);
        Button8=(Button)findViewById(R.id.button8);
        Button9=(Button)findViewById(R.id.button9);
        Button10=(Button)findViewById(R.id.button10);
        Button11=(Button)findViewById(R.id.button11);
        Button12=(Button)findViewById(R.id.button12);
        Button13=(Button)findViewById(R.id.button13);
        Button14=(Button)findViewById(R.id.button14);
        Button15=(Button)findViewById(R.id.button15);
        Button16=(Button)findViewById(R.id.button16);
        Button17=(Button)findViewById(R.id.button17);
        Button18=(Button)findViewById(R.id.button18);
        Button19=(Button)findViewById(R.id.button19);
        Button20=(Button)findViewById(R.id.button20);
        Button21=(Button)findViewById(R.id.button21);
        Button22=(Button)findViewById(R.id.button22);
        Button23=(Button)findViewById(R.id.button23);
        Button24=(Button)findViewById(R.id.button24);
        Button25=(Button)findViewById(R.id.button25);
        Button26=(Button)findViewById(R.id.button26);
        Button27=(Button)findViewById(R.id.button27);
        Button28=(Button)findViewById(R.id.button28);
        Button29=(Button)findViewById(R.id.button29);
        Button30=(Button)findViewById(R.id.button30);*/

        String REST_URI  = "http://192.168.100.25:8080/ServidorTEC/webapi/myresource/Mapa";
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
            Toast.makeText(Mapa.this,
                    resultado, Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(Mapa.this,
                    "Porfa llene el espacio de texto", Toast.LENGTH_LONG).show();
        }
    }

    public void SentGPS(View view){
        Button0=(Button)findViewById(R.id.button0);
        Button1=(Button)findViewById(R.id.button1);
        Button2=(Button)findViewById(R.id.button2);
        Button3=(Button)findViewById(R.id.button3);
        Button4=(Button)findViewById(R.id.button4);
        Button5=(Button)findViewById(R.id.button5);
        Button6=(Button)findViewById(R.id.button6);
        Button7=(Button)findViewById(R.id.button7);
        Button8=(Button)findViewById(R.id.button8);
        Button9=(Button)findViewById(R.id.button9);
        Button10=(Button)findViewById(R.id.button10);
        Button11=(Button)findViewById(R.id.button11);
        Button12=(Button)findViewById(R.id.button12);
        Button13=(Button)findViewById(R.id.button13);
        Button14=(Button)findViewById(R.id.button14);
        Button15=(Button)findViewById(R.id.button15);
        Button16=(Button)findViewById(R.id.button16);
        Button17=(Button)findViewById(R.id.button17);
        Button18=(Button)findViewById(R.id.button18);
        Button19=(Button)findViewById(R.id.button19);
        Button20=(Button)findViewById(R.id.button20);
        Button21=(Button)findViewById(R.id.button21);
        Button22=(Button)findViewById(R.id.button22);
        Button23=(Button)findViewById(R.id.button23);
        Button24=(Button)findViewById(R.id.button24);
        Button25=(Button)findViewById(R.id.button25);
        Button26=(Button)findViewById(R.id.button26);
        Button27=(Button)findViewById(R.id.button27);
        Button28=(Button)findViewById(R.id.button28);
        Button29=(Button)findViewById(R.id.button29);
        Button30=(Button)findViewById(R.id.button30);
        final Button[] B={Button0,Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9,
                Button10,Button11,Button12,Button13,Button14,Button15,Button16,Button17,Button18,
                Button19,Button20,Button21,Button22,Button23,Button24,Button25,Button26,Button27,
                Button28,Button29,Button30};
        Toast.makeText(Mapa.this,
                "hola"+B[2].getX(), Toast.LENGTH_LONG).show();

        String REST_URI  = "http://192.168.100.25:8080/ServidorTEC/webapi/myresource/Residencia";

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
                params.put("Carne","2018135360");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}
