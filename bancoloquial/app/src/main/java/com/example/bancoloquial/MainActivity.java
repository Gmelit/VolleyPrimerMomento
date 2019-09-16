package com.example.bancoloquial;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText etident, etclave;
    Button ingresar;
    RequestQueue rq;
    JsonRequest jrq;
    TextView tvRegistrar;
    Button btn,btneditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etclave = findViewById(R.id.etclave);
        etident = findViewById(R.id.etident);
        tvRegistrar = findViewById(R.id.tvRegistrar);
        btn = findViewById(R.id.button);
        rq = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarses();
            }
        });
        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(getApplicationContext(),RegistrarActivity.class);
                startActivity(in);
            }
        });
    }

    private void crearCliente(){
        Intent i = new Intent(this,RegistrarActivity.class);
        startActivity(i);
    }

    private void iniciarses() {
        String url = "http://192.168.1.57:90/bancoweb/sesion.php?ident="+etident.getText().toString()+"&clave="+etclave.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Nombre de usuario o contraseña incorrecto.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();

        //instanciar el objeto
        Cliente cli = new Cliente();

        JSONArray jsonusuario = response.optJSONArray("datos");

        JSONObject ocliente = null;
        try {
            ocliente = jsonusuario.getJSONObject(0);
            cli.setIdent(ocliente.optString("ident"));
            cli.setEmail(ocliente.optString("email"));
            cli.setNombres(ocliente.optString("nombre"));
            cli.setClave(ocliente.optString("clave"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Intent intencion = new Intent(this,InicioActivity.class);
        intencion.putExtra("nombre", cli.getNombres());
        intencion.putExtra("ident", cli.getIdent());
        intencion.putExtra("email", cli.getEmail());
        intencion.putExtra("clave", cli.getClave());
        startActivity(intencion);
    }


}
