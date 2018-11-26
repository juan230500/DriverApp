package com.example.juan.driverapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.media.Image;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private String viaje;
    //******agregado por mi////
    private Handler handler=new Handler();
    private Timer timer=new Timer();
    private LineView linea;
    private Button img;
    private float y;
    private float x;
    private float xf;
    private float yf;
    private float m;
    private float b;
    private int sumador;
    private Button[]botones;
    int posicionLugar;
    private int posActual;;
    //******agregado por mi////
    private String conductorCarne;
    private String asientos;


    private int[][] Mapa;

    private boolean registrado;
    private boolean viajar;

    private String[] Pasajeros;
    private String[] PosPasajeros;
    private int[] Ruta;
    private int[] Tiempos;
    private float xAcumulada;
    private float ETAfinal;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa);

        tv1=findViewById(R.id.ETA);
        entrada=(EditText)findViewById(R.id.editText);
        img=(Button)findViewById(R.id.buttonCarro);
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
        Button [] B={Button0,Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9,
                Button10,Button11,Button12,Button13,Button14,Button15,Button16,Button17,Button18,
                Button19,Button20,Button21,Button22,Button23,Button24,Button25,Button26,Button27,
                Button28,Button29,Button30};
        botones=B;
        String REST_URI  = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/Mapa";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        abrirCarne();
        if (!registrado){
            finish();
            startActivity(new Intent(this, RegistrarCarne.class));
        }else {
            abrirViaje();
            abrirAsientos();
            if (!viajar) {
                finish();
            }else{
                StringRequest stringRequest = new StringRequest(Request.Method.GET, REST_URI,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Gson gson=new Gson();
                                Mapa=gson.fromJson(response,int[][].class);
                                Toast.makeText(Mapa.this,
                                        "Mapa cargado exitosamente", Toast.LENGTH_SHORT).show();
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
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("miviaje.txt", Activity.MODE_PRIVATE));
            archivo_wr.write("20");
            archivo_wr.flush();
            archivo_wr.close();
            viajar=false;
        } catch (IOException e){}
    }



    /**
     * Este método
     * @param view este corresponde al view de la aplicación
     */
    public void giveMeConexiones(View view){
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


    public void go2(final int i){
        xf=botones[Ruta[i]].getX();
        yf=botones[Ruta[i]].getY();
        m=(yf-y)/(xf-x);
        b=y-m*x;
        if (x-xf<0){
            sumador=2;
        }
        else{
            sumador=-2;
        }
        xf+=sumador*5;
        double hipo=Math.sqrt((x-xf)*(x-xf)+(y-yf)*(y-yf));
        int t=Tiempos[i-1]*1000;
        int velocidad= (int) ((2*t)/hipo);
        //https://www.youtube.com/watch?v=UxbJKNjQWD8
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (Math.abs(x-xf)>10){
                            x+=sumador;
                            y=x*m+b;
                            img.setX(x);
                            img.setY(y);
                            xAcumulada-=2;
                            tv1.setText("ETA= "+(xAcumulada)/ETAfinal);
                        }
                        else{
                            timer.cancel();
                            timer.purge();
                            timer=new Timer();
                            request(i+1);
                        }
                        Log.i("Mapa", "HOLA");
                    }
                });
            }
        },0,velocidad);
    }
    public void  calcularXacumulada(){
        int pos=0;
        xAcumulada=0;
        while(pos<Ruta.length-1){
           xAcumulada+=Math.abs(botones[Ruta[pos]].getX()-botones[Ruta[pos+1]].getX());
           pos+=1;
        }
        
    }
    public void request(final int i){
        if (i==Ruta.length){
            CerrarViaje();
            return;
        }
        String REST_URI  = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/ActualizarViaje";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("1")){
                            go2(i);
                        }
                        else{
                            parsear2(response);
                            Toast.makeText(Mapa.this,
                                    response, Toast.LENGTH_LONG).show();
                            Toast.makeText(Mapa.this,
                                    Arrays.toString(Ruta), Toast.LENGTH_LONG).show();
                            Toast.makeText(Mapa.this,
                                    Arrays.toString(Tiempos), Toast.LENGTH_LONG).show();
                            go2(1);
                        }

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
                params.put("Pos", ""+Ruta[i-1]);
                params.put("Carne",conductorCarne);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void SentGPS(View view){
        String IsSolo;
        if (viaje.equals("0")){
            IsSolo="RutaSolo";
        }
        else{
            IsSolo="RutaAmigo";
        }

        String REST_URI  = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/"+IsSolo;

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Mapa.this,
                                response, Toast.LENGTH_LONG).show();
                        parsear(response);
                        calcularXacumulada();
                        Log.i("Carpooling", "aco: "+xAcumulada);
                        guardarPasajeros();
                        x=botones[Ruta[0]].getX();
                        y=botones[Ruta[0]].getY();
                        go2(1);
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
                params.put("Carne",conductorCarne);
                params.put("Asientos",asientos);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void parsear2(String response){
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);


        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();

            JsonArray RutaDetails = details.getAsJsonArray("Ruta");

            Ruta=new int[RutaDetails.size()];
            Tiempos=new int[RutaDetails.size()];

            for (int i = 0; i < RutaDetails.size(); i++) {
                JsonPrimitive value = RutaDetails.get(i).getAsJsonPrimitive();
                Ruta[i]=value.getAsInt();
            }

            JsonArray TiemposDetails = details.getAsJsonArray("Tiempos");

            for (int i = 0; i < TiemposDetails.size(); i++) {
                JsonPrimitive value = TiemposDetails.get(i).getAsJsonPrimitive();
                Tiempos[i]=value.getAsInt();
                //Toast.makeText(ListaAmigos.this, value.getAsString(), Toast.LENGTH_LONG).show();
            }

        }
    }



    /**
     * Este método hace parse a los datos en json y los coloca en los arrays correspondientes
     * @param response corresponde al string en json que se desea parsear
     */
    public void parsear(String response){
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);


        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();

            JsonArray PasajerosDetails = details.getAsJsonArray("Pasajeros");

            Pasajeros=new String[PasajerosDetails.size()];
            PosPasajeros=new String[PasajerosDetails.size()];

            for (int i = 0; i < PasajerosDetails.size(); i++) {
                JsonPrimitive value = PasajerosDetails.get(i).getAsJsonPrimitive();
                Pasajeros[i]=value.getAsString();
            }

            JsonArray PosPasajerosDetails = details.getAsJsonArray("PosicionesPasajeros");

            for (int i = 0; i < PosPasajerosDetails.size(); i++) {
                JsonPrimitive value = PosPasajerosDetails.get(i).getAsJsonPrimitive();
                PosPasajeros[i]=value.getAsString();
                //Toast.makeText(ListaAmigos.this, value.getAsString(), Toast.LENGTH_LONG).show();
            }

            JsonArray RutaDetails = details.getAsJsonArray("Ruta");

            Ruta=new int[RutaDetails.size()];
            Tiempos=new int[RutaDetails.size()];

            for (int i = 0; i < RutaDetails.size(); i++) {
                JsonPrimitive value = RutaDetails.get(i).getAsJsonPrimitive();
                Ruta[i]=value.getAsInt();
            }

            JsonArray TiemposDetails = details.getAsJsonArray("Tiempos");
            ETAfinal=0;
            for (int i = 0; i < TiemposDetails.size(); i++) {
                JsonPrimitive value = TiemposDetails.get(i).getAsJsonPrimitive();
                Tiempos[i]=value.getAsInt();
                ETAfinal+=Tiempos[i];
                //Toast.makeText(ListaAmigos.this, value.getAsString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void CerrarViaje(){
        String REST_URI  = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/CerrarViaje";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Mapa.this,
                                "Viaje cerrado "+response, Toast.LENGTH_LONG).show();
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
                params.put("Carne",conductorCarne);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void abrirViaje(){
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("miviaje.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            viaje = br.readLine();
        } catch (IOException e){}
        if (viaje==null){
            Toast.makeText(Mapa.this,
                    "Debe seleccionar la forma de viaje antes de continuar.", Toast.LENGTH_SHORT).show();
            viajar=false;
        }
        else if (viaje.equals("20")) {
            Toast.makeText(Mapa.this,
                    "Debe seleccionar la forma de viaje antes de continuar.", Toast.LENGTH_SHORT).show();
            viajar=false;

        }
        else{
            viajar=true;
            Toast.makeText(Mapa.this,
                    "Viaje escogido: " + viaje, Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirAsientos(){
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("misasientos.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            asientos = br.readLine();
        } catch (IOException e){}
    }

    public void abrirCarne(){
        String archivos []  = fileList();
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("micarne.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            conductorCarne = br.readLine();
        } catch (IOException e){}
        if (conductorCarne!=null) {
            registrado= true;
            Toast.makeText(Mapa.this,
                    "Carnet del conductor: " + conductorCarne, Toast.LENGTH_SHORT).show();
        }
        else {
            registrado= false;
            Toast.makeText(Mapa.this,
                    "Es necesario que registre su carnet para continuar." , Toast.LENGTH_SHORT).show();
        }
    }
    public void guardarPasajeros(){
        //Toast.makeText(this, "Pasajeros :" + Pasajeros, Toast.LENGTH_SHORT).show();
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("mispasajeros.txt", Activity.MODE_PRIVATE));
            for (String p: Pasajeros) {
                Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
                archivo_wr.write(p + "\n");
            }
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }

}
