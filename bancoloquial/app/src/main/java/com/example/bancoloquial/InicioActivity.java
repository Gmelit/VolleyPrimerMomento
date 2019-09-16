package com.example.bancoloquial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class InicioActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private String identPer, nombresPer, emailPer, clavePer, saldo, nrocuenta;
    TextView txtNom, txtEmail, txtIdent, txtSaldo, txtCuenta;
    Button btnEliminarPer, btnTransferenciaPer, btnEditarPer, btnConsultar;
    RequestQueue rq;
    JsonRequest jrq;

    //instanciar el objeto
    Saldo sal = new Saldo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        txtNom = findViewById(R.id.txtNombre);
        txtSaldo = findViewById(R.id.txtsaldo);
        txtEmail = findViewById(R.id.txtEmail);
        txtIdent = findViewById(R.id.txtIdentificacion);
        txtCuenta = findViewById(R.id.txtnrocuenta);

        rq = Volley.newRequestQueue(this);

        btnConsultar=findViewById(R.id.btnConsultar);
        btnEliminarPer = findViewById(R.id.btneliminar);
        btnEditarPer = findViewById(R.id.btnEditar);
        btnTransferenciaPer = findViewById(R.id.btnTransferencia);

        txtNom.setText(getIntent().getStringExtra("nombre"));
        txtEmail.setText(getIntent().getStringExtra("email"));
        txtIdent.setText(getIntent().getStringExtra("ident"));
        clavePer = getIntent().getStringExtra("clave");

        btnEliminarPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCliente("http://192.168.1.57:90/bancoweb/elimina.php");
            }
        });

        btnEditarPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),EditarActivity.class);
                intencion.putExtra("nombre", txtNom.getText());
                intencion.putExtra("ident", txtIdent.getText());
                intencion.putExtra("email", txtEmail.getText());
                intencion.putExtra("clave", clavePer);
                startActivity(intencion);
            }
        });

        btnTransferenciaPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),TransaActivity.class);
                intencion.putExtra("nombre", txtNom.getText());
                intencion.putExtra("ident", txtIdent.getText());
                intencion.putExtra("email", txtEmail.getText());
                intencion.putExtra("clave", clavePer);
                intencion.putExtra("saldo",sal.getSaldo());
                intencion.putExtra("nrocuenta",sal.getNrocuenta());
                startActivity(intencion);
            }
        });
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(getApplicationContext(),TransaccionesActivity.class);
                startActivity(c);
            }
        });
        getSaldo(); // consulta el saldo cuando inicia la activity

    }

    private void getSaldo(){
        String url = "http://192.168.1.57:90/bancoweb/getsaldo.php?ident="+txtIdent.getText();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    private void eliminarCliente(String url) {

        final String cedula = txtIdent.getText().toString();
        StringRequest rqusuario = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Se ha eliminado su cuenta", Toast.LENGTH_LONG).show();

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
                parametros.put("ident", cedula);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqusuario);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No se pudo consultar el saldo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonusuario = response.optJSONArray("datos");

        JSONObject saldoC = null;
        try {
            saldoC = jsonusuario.getJSONObject(0);
            sal.setNrocuenta(saldoC.optString("nrocuenta"));
            sal.setIdent(saldoC.optString("ident"));
            sal.setFecha(saldoC.optString("fecha"));
            sal.setSaldo(saldoC.optString("saldo"));
            //Toast.makeText(this, "Saldo recolectado", Toast.LENGTH_SHORT).show();
            txtSaldo.setText("Saldo actual: $ "+sal.getSaldo());
            txtCuenta.setText("nro de cuenta: "+sal.getNrocuenta());

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
