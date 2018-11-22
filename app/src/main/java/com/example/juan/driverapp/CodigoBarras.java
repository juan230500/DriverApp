package com.example.juan.driverapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CodigoBarras extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private String codigoBarras;
    private ZXingScannerView scannerView;
    private int codigoPermiso = 1;
    private boolean repetido = false;


    public void Escanear(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            this.scannerView = new ZXingScannerView(this);
            setContentView(this.scannerView);
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        } else {
            pedirPermiso();
        }

    }

    public void pedirPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permisos de cámara desactivados")
                    .setMessage("Se requieren permisos del uso de cámara para escanear el código de barras en su carné del TEC")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CodigoBarras.this, new String[]{Manifest.permission.CAMERA}, codigoPermiso);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, codigoPermiso);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_barras);
    }


    public void handleResult(Result result) {
        scannerView.stopCamera();
        setContentView(R.layout.activity_codigo_barras);
        if (result.getText().length() != 10) {
            new AlertDialog.Builder(this)
                    .setTitle("El codigo escaneado no pertenece a un carné TEC")
                    .setMessage("Por favor intentelo de nuevo")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            this.codigoBarras = result.getText();
            new AlertDialog.Builder(this)
                    .setTitle("El carné de su amigo es")
                    .setMessage(this.codigoBarras)
                    .setPositiveButton("Correcto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(CodigoBarras.this, "Amigo agregado!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            registrar();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new AlertDialog.Builder(CodigoBarras.this)
                                    .setTitle("Vuelva a intentarlo")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
                    })
                    .create().show();
        }
    }




    public void archivar(){
        String archivos []  = fileList();

        if (archivoExiste(archivos, "amigos.txt")){
            try {
                InputStreamReader archivo_rd = new InputStreamReader(openFileInput("amigos.txt"));
                BufferedReader br = new BufferedReader(archivo_rd);
                String linea = br.readLine();
                String lista_completa = "";



                while ((linea != null) && !repetido) {
                    lista_completa = lista_completa + linea + "\n";
                    linea = br.readLine();
                    try {
                        if (linea.equals(codigoBarras) || linea == null) {
                            repetido = true;
                            new AlertDialog.Builder(CodigoBarras.this)
                                    .setTitle("Este amigo ya ha sido agregado antes, pruebe con otro")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
                    } catch (NullPointerException e) {
                        br.close();
                        archivo_rd.close();
                        guardar(lista_completa);
                    }
                }

            } catch (IOException e){

            }
        } else {
            guardar("");
        }

    }

    public void guardar(String codigos){
        Toast.makeText(this, "aqui", Toast.LENGTH_LONG);
        if (codigoBarras.length() == 10){
            registrar();
            String codigos_nuevo = codigos + codigoBarras + "\n";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Amigos");
            builder.setMessage(codigos_nuevo);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            try {
                OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("amigos.txt", Activity.MODE_PRIVATE));
                archivo_wr.write(codigos_nuevo);
                archivo_wr.flush();
                archivo_wr.close();
            } catch (IOException e){

            }
            Toast.makeText(this, "Amigo agregado!", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, "Error al agregar amigo", Toast.LENGTH_LONG);
        }

    }

    public boolean archivoExiste(String archivos [], String nombre){
        for (int i = 0; i <archivos.length; i++) {
            if (nombre.equals("amigos.txt")) {
                return true;
            }
        }
        return false;
    }

    public void registrar(){
        Toast.makeText(this, "Amigo agregado!", Toast.LENGTH_LONG);
        String REST_URI  = "http://192.168.100.13:8080/ServidorTEC/webapi/myresource/Carne";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CodigoBarras.this,
                                "Sent "+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Carne", "" + codigoBarras);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
