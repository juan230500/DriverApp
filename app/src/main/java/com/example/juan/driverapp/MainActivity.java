package com.example.juan.driverapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Configuracion.OnFragmentInteractionListener {

    private String viaje;
    private String asientos;
    private TextView txtv;
    private TextView txtc;
    private String carne;
    private String ip = "192.168.100.12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String REST_URI  = "http://" + ip + ":8080/ServidorTEC/webapi/myresource/CalificacionPropia";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Double num = (Double.parseDouble(response)) * 0.01;
                        txtv = (TextView)findViewById(R.id.textViewCalif);
                        txtv.setText(num.toString());
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
                params.put("Carne", "" + carne);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("miviaje.txt", Activity.MODE_PRIVATE));
            archivo_wr.write("20");
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_viaje1) {
            return true;
        }else if (id == R.id.action_viaje2) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment seleccionado = null;
        Intent seleccionado2 = null;
        Boolean haySeleccion= true;
        Boolean haySeleccion2= false;

        if (id == R.id.nav_inicio) {
            seleccionado2 = new Intent(this, Mapa.class);
            haySeleccion= false;
            haySeleccion2= true;
        }else if (id == R.id.nav_camera) {
            seleccionado2 = new Intent(this, CodigoBarras.class);
            haySeleccion= false;
            haySeleccion2= true;

        }
        else if (id == R.id.Top5) {
            seleccionado2 = new Intent(this, Top5.class);
            haySeleccion= false;
            haySeleccion2= true;
        }
        else if (id == R.id.nav_linkedin) {
            seleccionado2 = new Intent(this, LoginLinkedIn.class);
            haySeleccion = false;
            haySeleccion2= true;
        } else if (id == R.id.nav_carne) {
            seleccionado2 = new Intent(this, RegistrarCarne.class);
            haySeleccion = false;
            haySeleccion2= true;
        } else if (id == R.id.nav_manage) {
            seleccionado = new Configuracion();
            haySeleccion = true;
            haySeleccion2= false;
        }else if (id == R.id.nav_calificar) {
            seleccionado2 = new Intent(this, Calificar.class);
            haySeleccion= false;
            haySeleccion2= true;
        } else if (id == R.id.nav_amigos) {
            seleccionado2 = new Intent(this, ListaAmigos.class);
            haySeleccion = false;
            haySeleccion2= true;
        }
        if(haySeleccion){
            getSupportFragmentManager().beginTransaction().replace(R.id.Panel, seleccionado).commit();
        } else if(haySeleccion2){
            startActivity(seleccionado2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void guardarSD(View view){
        Toast.makeText(this, "Ha seleccionado viaje sin desvíos.", Toast.LENGTH_SHORT).show();
        Intent asientos = new Intent(this, Asientos.class);
        startActivity(asientos);
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("miviaje.txt", Activity.MODE_PRIVATE));
            archivo_wr.write("0");
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }
    public void guardarAmigos(View view){
        Toast.makeText(this, "Ha seleccionado viaje con amigos.", Toast.LENGTH_SHORT).show();
        Intent asientos = new Intent(this, Asientos.class);
        startActivity(asientos);
        try {
            OutputStreamWriter archivo_wr = new OutputStreamWriter(openFileOutput("miviaje.txt", Activity.MODE_PRIVATE));
            archivo_wr.write("1");
            archivo_wr.flush();
            archivo_wr.close();
        } catch (IOException e){}
    }

    public void abrir(){
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("miviaje.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            viaje = br.readLine();
        } catch (IOException e){}
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }public void abrirCarne(){
        try {
            InputStreamReader archivo_rd = new InputStreamReader(openFileInput("micarne.txt"));
            BufferedReader br = new BufferedReader(archivo_rd);
            carne = br.readLine();
        } catch (IOException e){}
    }
    public void refresh(View view){
        abrirCarne();
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        String REST_URI  = "http://" + ip + ":8080/ServidorTEC/webapi/myresource/CalificacionPropia";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REST_URI,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Double num = (Double.parseDouble(response)) * 0.01;
                        txtv = (TextView)findViewById(R.id.textViewCalif);
                        txtc= findViewById(R.id.TextCarne);
                        txtc.setText(carne);
                        txtv.setText(num.toString());
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
                params.put("Carne", "" + carne);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
