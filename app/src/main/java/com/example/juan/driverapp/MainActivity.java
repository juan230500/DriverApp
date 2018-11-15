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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
