package com.example.juan.driverapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * Esta clase corresponde a la pantalla donde el conductor escoge la cantidad de asientos disponibles
 */
public class Asientos extends AppCompatActivity {

    private int cantAsientos;
    private ImageView imgAsientos;
    private boolean seleccionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asientos);
        this.imgAsientos = (ImageView) findViewById(R.id.imgasientos);
    }

    /**
     * Este método define la cantidad de asientos a 1, además cambia la imagen que representa la cantidad de asientos escogidos
     * @param view corresponde al view de la aplicación
     */
    public void asiento1(View view){
        cantAsientos = 1;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos1", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    /**
     * Este método define la cantidad de asientos a 2, además cambia la imagen que representa la cantidad de asientos escogidos
     * @param view corresponde al view de la aplicación
     */
    public void asiento2(View view){
        cantAsientos = 2;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos2", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    /**
     * Este método define la cantidad de asientos a 3, además cambia la imagen que representa la cantidad de asientos escogidos
     * @param view corresponde al view de la aplicación
     */
    public void asiento3(View view){
        cantAsientos = 3;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos3", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    /**
     * Este método define la cantidad de asientos a 4, además cambia la imagen que representa la cantidad de asientos escogidos
     * @param view corresponde al view de la aplicación
     */
    public void asiento4(View view){
        cantAsientos = 4;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos4", null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imgAsientos.setImageResource(imageResource);
    }

    /**
     * Este método valida si ya se ha seleccionado al menos una opción de la cantidad de asientos, luego manda los datos a guardar y finaliza la actividad
     * @param view corresponde al view de la aplicación
     */
    public void ok(View view) {
        if (seleccionado) {
            this.finish();
            guardarAsientos();
        } else {
            cantAsientos=0;
            this.finish();
            guardarAsientos();
            Toast.makeText(this, "Ha elejido 0 asientos", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Este método guarda la cantidad de asientos seleccionados en un fichero
     */
    public void guardarAsientos(){
        Toast.makeText(this, "Asientos disponibles: "+cantAsientos, Toast.LENGTH_LONG).show();
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("misasientos.txt", Activity.MODE_PRIVATE));
            archivo_wr.write(cantAsientos+"");
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }
}
