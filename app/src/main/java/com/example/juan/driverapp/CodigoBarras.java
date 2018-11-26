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

/**
 * Esta clase corresponde a la pantalla en donde se agregan a los amigos mediante el lector de códigos de barra
 */
public class CodigoBarras extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private String codigoBarras;
    private String conductorCarne;
    private ZXingScannerView scannerView;
    private int codigoPermiso = 1;
    private boolean repetido = false;


    /**
     * Este método cambia el view a la camara con la cual se escaneará el código de barras
     * @param view corresponde al view de la aplicación
     */
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

    /**
     * Este método pide los permisos necesarios de uso de la cámara al usuario
     */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_barras);
        abrir();
    }

    @Override
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

    /**
     * Este método almacena los carnés amigos escaneados en un fichero
     */
    public void abrir(){
        String archivos []  = fileList();
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("micarne.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            conductorCarne = br.readLine();
        } catch (IOException e){}
    }


    /**
     * Este método permite enviar los carnes de amigos correspondientes al carné de conductor registrado
     */
    public void registrar(){
        Toast.makeText(this, "Amigo agregado!", Toast.LENGTH_LONG);
        String REST_URI  = "http://192.168.100.12:8080/ServidorTEC/webapi/myresource/NuevoAmigo";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CodigoBarras.this,
                                "Sevidor: "+response, Toast.LENGTH_LONG).show();
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
                params.put("Conductor", "" + conductorCarne);
                params.put("Amigo", "" + codigoBarras);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
