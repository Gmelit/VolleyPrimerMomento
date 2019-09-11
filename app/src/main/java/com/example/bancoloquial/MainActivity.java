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
    EditText etident,etclave;
    Button ingresar;
    RequestQueue rq;
    JsonRequest jrq;
    TextView tvRegistrar;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etclave=findViewById(R.id.etclave);
        etident=findViewById(R.id.etident);
        tvRegistrar=findViewById(R.id.tvRegistrar);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarservicio("http:172.16.22.19:8087/Volley/login.php");
            }
        });
    }
    public void ingreasr() {
        Intent in=new Intent(getApplicationContext (),InicioActivity.class);
        in.putExtra ( "ident",etident.getText ().toString ());
        startActivity ( in );
    }

    public void registrar(View view) {
        Intent inte = new Intent(getApplicationContext(), RegistrarActivity.class);
        startActivity(inte);
    }


    private void ejecutarservicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim ().equals ( "success" )){
                    ingreasr ();
                }else{
                    Toast.makeText ( getApplicationContext (),"Error este usuario no existe",Toast.LENGTH_SHORT ).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros=new HashMap<String, String>();
                parametros.put("ident",etident.getText().toString());
                parametros.put("clave",etclave.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse ( VolleyError error ) {

    }

    @Override
    public void onResponse ( JSONObject response ) {

        Cliente cli = new Cliente();

        JSONArray jsonusuario = response.optJSONArray("info");

        JSONObject ocliente = null;
        try {
            ocliente = jsonusuario.getJSONObject(0);
            cli.setIdent(ocliente.optString ("ident"));
            cli.setEmail(ocliente.optString("email"));
            cli.setNombres (ocliente.optString("nombre"));
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
