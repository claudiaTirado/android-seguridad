package com.example.proyecto_security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static SecretKeySpec secret;
    static String clave="claudiatiradopra";
    private String encriptada ;
    String desencriptada;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText email;
    private EditText contraseña;
    private static String salt = "ssshhhhhhhhhhh!!!!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        email = (EditText) findViewById(R.id.txtEmail);
        contraseña = (EditText) findViewById(R.id.txtContraseña);
        Button registrar = (Button) findViewById(R.id.btRegistrar);
        Button entrar = (Button) findViewById(R.id.btEntrar);

registrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }
});
entrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        entrar();

    }
});
    }

private void entrar(){
    String correo = email.getText().toString().trim();
    String contrase = contraseña.getText().toString().trim();
    //guardar en interno
    SharedPreferences preferencias=getSharedPreferences("bdclaudia", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=preferencias.edit();
    editor.putBoolean("isValid",true);
    editor.putString("email", email.getText().toString());
    editor.putString("contraseña", contraseña.getText().toString());
    editor.commit();
//encriptar y desencriptar


        encriptada= encriptar(contrase,clave);
        desencriptada=desencriptar(encriptada,clave);
        Toast.makeText(getApplicationContext(),"encriptada"+encriptada,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"desencriptada"+desencriptada,Toast.LENGTH_LONG).show();


    //Verificamos que las cajas de texto no esten vacías
    if (TextUtils.isEmpty(correo)) {
        Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
        return;
    }

    if (TextUtils.isEmpty(contrase)) {
        Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
        return;
    }


    mAuth.signInWithEmailAndPassword(correo,desencriptada)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(MainActivity.this, "Se ha logueado el usuario con el email: " + email.getText().toString(), Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent ob=new Intent(getApplicationContext(),Home.class);
                        startActivity(ob);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorecto ",Toast.LENGTH_SHORT).show();

                    }

                    // ...
                }
            });
}
    public static String encriptar(String strToEncrypt, String secret)
    {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));

        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }



    public static String desencriptar(String strToDecrypt, String secret) {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
