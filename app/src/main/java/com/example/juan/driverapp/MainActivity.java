package com.example.juan.driverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListaAmigos.OnFragmentInteractionListener, Configuracion.OnFragmentInteractionListener {

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
        return true;
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
            return true;
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
        } else if (id == R.id.nav_linkedin) {
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
        } else if (id == R.id.nav_amigos) {
            seleccionado = new ListaAmigos();
            haySeleccion = true;
            haySeleccion2= false;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void login(View view){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                Toast.makeText(getApplicationContext(),"inicio de sesión",Toast.LENGTH_LONG).show();
                Go();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Toast.makeText(getApplicationContext(),"error en inicio de sesión"+error.toString(),Toast.LENGTH_LONG).show();
            }
        },true);
    }



    private static Scope buildScope(){
        return Scope.build(Scope.R_BASICPROFILE,Scope.R_EMAILADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,requestCode,resultCode,data);
    }

    public void Go(){
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
    }

}
