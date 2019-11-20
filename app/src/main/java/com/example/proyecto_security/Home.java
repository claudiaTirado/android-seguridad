<<<<<<< HEAD
package com.example.proyecto_security;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView nombre = (TextView) findViewById(R.id.labelNombre);
        TextView email3= (TextView) findViewById(R.id.labelEmail);
        TextView lotLat = (TextView) findViewById(R.id.labelLot);

        Button contact1 = (Button) findViewById(R.id.btncon1);
        Button contact2 = (Button) findViewById(R.id.btnCon2);

        EditText number1 = (EditText) findViewById(R.id.txtNum1);
        EditText number2 = (EditText) findViewById(R.id.txtNum2);

        Button guardar = (Button) findViewById(R.id.btnGuardar);


guardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
    }
}
=======
package com.example.proyecto_security;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView nombre = (TextView) findViewById(R.id.labelNombre);
        TextView email3= (TextView) findViewById(R.id.labelEmail);
        TextView lotLat = (TextView) findViewById(R.id.labelLot);

        Button contact1 = (Button) findViewById(R.id.btncon1);
        Button contact2 = (Button) findViewById(R.id.btnCon2);

        EditText number1 = (EditText) findViewById(R.id.txtNum1);
        EditText number2 = (EditText) findViewById(R.id.txtNum2);

        Button guardar = (Button) findViewById(R.id.btnGuardar);


guardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
    }
}
>>>>>>> parent of d1ff9fb... Segunda entrega encriptacion
