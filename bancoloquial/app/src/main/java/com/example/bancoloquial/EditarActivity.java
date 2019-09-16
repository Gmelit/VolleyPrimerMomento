package com.example.bancoloquial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity {
    String edNombres, edEmail, edClave, edIdent;
    EditText etNombres, etEmail, etClave, etIdent;
    TextView textView2;
    Button btnEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        etNombres = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etClave = findViewById(R.id.etContra1);

        textView2 = findViewById(R.id.textView5);
        btnEditar = findViewById(R.id.BtnEditar);

        etNombres.setText(getIntent().getStringExtra("nombre"));
        etEmail.setText(getIntent().getStringExtra("email"));
        etClave.setText(getIntent().getStringExtra("clave"));




        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEdit("http://192.168.1.57:90/bancoweb/actualiza.php");
            }
        });
    }
    private void enviarEdit(String url) {

        edIdent = getIntent().getStringExtra("ident");
        edNombres = etNombres.getText().toString();
        edEmail = etEmail.getText().toString();
        edClave = etClave.getText().toString();


        StringRequest rqusuario = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Edición correcta.", Toast.LENGTH_LONG).show();

                Intent intencion = new Intent(getApplicationContext(),InicioActivity.class);
                intencion.putExtra("nombre", edNombres);
                intencion.putExtra("ident",  edIdent);
                intencion.putExtra("email", edEmail);
                intencion.putExtra("clave", edClave);
                startActivity(intencion);

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
                parametros.put("ident",edIdent);
                parametros.put("nombre",edNombres);
                parametros.put("email",edEmail);
                parametros.put("clave",edClave);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqusuario);


    }
}
