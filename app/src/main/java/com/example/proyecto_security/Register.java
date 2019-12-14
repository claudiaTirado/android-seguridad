package com.example.proyecto_security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Register extends AppCompatActivity {
    private static SecretKeySpec secret;
    static String clave="claudiatiradopra";
    private String encriptada ;
    private  String desencriptada;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText nombre;
    private EditText email2;
    private EditText contraseña2;
    private EditText confirmacionCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        nombre =(EditText)findViewById(R.id.editTextName);
        email2 =(EditText) findViewById(R.id.txtEmail2);
        contraseña2 = (EditText) findViewById(R.id.txtContraseña2);
        confirmacionCon = (EditText) findViewById(R.id.txtConfirmarCon);

        Button login =(Button) findViewById(R.id.btLogin);
        Button registrar2 = (Button) findViewById(R.id.btRegistrar2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        registrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();

            }
        });
    }

    private void registrarUsuario() {
        final String nombree= nombre.getText().toString().trim();
        final String correo = email2.getText().toString().trim();
        final String uid="fghgfhfgh";
        String contraseña = contraseña2.getText().toString().trim();
        String confirmar = confirmacionCon.getText().toString().trim();



        MainActivity ob = new MainActivity();


            encriptada = ob.encriptar(contraseña, clave);
            desencriptada = ob.desencriptar(encriptada, clave);



        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmar)) {
            Toast.makeText(this, "Falta ingresar la confirmacion contraseña", Toast.LENGTH_LONG).show();
            return;
        }


Log.d("TAG",confirmar+contraseña);

            FirebaseUser currentUser = mAuth.getCurrentUser();


        if(contraseña.equals(confirmar)){

            if (currentUser != null) {

                mAuth.createUserWithEmailAndPassword(correo, desencriptada)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Se ha registrado el usuario con el email: " + email2.getText(), Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    BaseDeDatosHelper db = new BaseDeDatosHelper(getApplicationContext());
                                    db.insert(nombree,correo,user.getUid(),getApplicationContext());
                                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(main);
                                } else {

                                    Toast.makeText(Register.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }

    }else Toast.makeText(this, "Las contraseñas no coinciden, intente de nuevo", Toast.LENGTH_LONG).show();

    }



}
