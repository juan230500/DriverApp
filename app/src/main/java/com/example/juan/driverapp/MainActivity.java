package com.example.juan.driverapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText entrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada=(EditText)findViewById(R.id.editText);

    }
    public void SentGPS(View view){
        Toast.makeText(MainActivity.this,
                "Sent "+entrada.getText(), Toast.LENGTH_LONG).show();
    }

}
