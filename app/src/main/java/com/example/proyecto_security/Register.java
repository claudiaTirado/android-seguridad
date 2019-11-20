<<<<<<< HEAD
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
    byte[] encriptada = new byte[0];
    String desencriptada;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText email2;
    private EditText contraseña2;
    private EditText confirmacionCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
        String correo = email2.getText().toString().trim();
        String contraseña = contraseña2.getText().toString().trim();
        String confirmar = confirmacionCon.getText().toString().trim();

        //encriptamos la contraseña

        try {
            SecretKey secret = generateKey();
            encriptada = encryptMsg(contraseña, secret);
            desencriptada = decrryptMsg(encriptada, secret);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //Verificamos que las cajas de texto no esten vacías
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
            //creating a new user

            if ((currentUser != null) && (contraseña.equals(confirmar))) {

                mAuth.createUserWithEmailAndPassword(correo, desencriptada)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if success
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Se ha registrado el usuario con el email: " + email2.getText(), Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent ob = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(ob);
                                } else {

                                    Toast.makeText(Register.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();

            }

    }


    //clave
    public static SecretKey generateKey()throws NoSuchAlgorithmException, InvalidKeyException {
        return secret = new SecretKeySpec(clave.getBytes(),"AES");
    }
    //encriptar
    public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;    }
    //desencriptar
    public static String decrryptMsg(byte[] cipherText, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }
}
=======
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

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText email2;
    private EditText contraseña2;
    private EditText confirmacionCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
               // Toast.makeText(getApplicationContext(),"boton",Toast.LENGTH_LONG).show();
               Intent intent = new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);
            }
        });
    }

    private void registrarUsuario(){
        String correo = email2.getText().toString().trim();
        String contraseña = contraseña2.getText().toString().trim();
        String confirmar = confirmacionCon.getText().toString().trim();

        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"campo email vacio ",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(contraseña)){
            Toast.makeText(this,"campo ontraseña vacio ",Toast.LENGTH_LONG).show();
            return;
        }



        FirebaseUser currentUser = mAuth.getCurrentUser();

       // if (currentUser != null) {
            mAuth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "registro completo: " + email2.getText(), Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {

                                Toast.makeText(Register.this, "No se pudo registrar ", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
       // }


    }
}
>>>>>>> parent of d1ff9fb... Segunda entrega encriptacion
