package com.example.bancoloquial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class RegistrarActivity extends AppCompatActivity {
EditText etIdent,etNombre,etCorreo,etClave,etClave2;
Button btnRegistrar;
    String ident, nombre, email, clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        etIdent=findViewById(R.id.etIdentidad);
        etNombre=findViewById(R.id.etNombre);
        etCorreo=findViewById(R.id.etEmail);
        etClave=findViewById(R.id.etContra1);
        etClave2=findViewById(R.id.etContra2);
        btnRegistrar=findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario("http://192.168.1.57:90/bancoweb/registro.php");
                crearCuenta("http://192.168.1.57:90/bancoweb/crearcuenta.php");
            }
        });
    }
    private void crearCuenta(String s) {
        ident = etIdent.getText().toString();

        StringRequest rqusuario = new StringRequest(Request.Method.POST, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Cuenta creada", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            // Pasa los datos en formato JSON a la BD
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("ident",ident);
                parametros.put("saldo","50000");
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqusuario);
    }

    private void registrarUsuario(String url){

        ident = etIdent.getText().toString();
        nombre = etNombre.getText().toString();
        email = etCorreo.getText().toString();
        clave = etClave.getText().toString();


        StringRequest rqusuario = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            // Pasa los datos en formato JSON a la BD
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("ident",ident);
                parametros.put("nombre",nombre);
                parametros.put("email",email);
                parametros.put("clave",clave);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqusuario);
    }
}
