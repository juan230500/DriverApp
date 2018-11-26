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

    public void asiento1(View view){
        cantAsientos = 1;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos1", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    public void asiento2(View view){
        cantAsientos = 2;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos2", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    public void asiento3(View view){
        cantAsientos = 3;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos3", null, getPackageName());
        imgAsientos.setImageResource(imageResource);
    }

    public void asiento4(View view){
        cantAsientos = 4;
        seleccionado = true;
        int imageResource = getResources().getIdentifier("@drawable/asientos4", null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imgAsientos.setImageResource(imageResource);
    }

    public void ok(View view) {
        if (seleccionado) {
            this.finish();
            guardarAsientos();
        } else {
            Toast.makeText(this, "No ha seleccionado la cantidad de asientos", Toast.LENGTH_LONG).show();
        }
    }

    public void guardarAsientos(){
        Toast.makeText(this, "Asientos disponibles: ", Toast.LENGTH_LONG).show();
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("misasientos.txt", Activity.MODE_PRIVATE));
            archivo_wr.write(cantAsientos+"");
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }
}
