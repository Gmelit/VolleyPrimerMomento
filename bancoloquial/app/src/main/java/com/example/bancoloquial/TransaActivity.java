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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TransaActivity extends AppCompatActivity {
    String identTrans, saldoTrans, nrocuentaTrans;
    EditText etCuentaDestino, etCantidad;
    Button btnEnviar;
    RequestQueue rqSaldo;
    JsonRequest jrqSaldo;
    TextView txtSaldoTrans, txtCuentaTrans;
    boolean todoBien1 = false, todoBien2 = false;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_transa );
        saldoTrans = getIntent().getStringExtra("saldo");
        nrocuentaTrans = getIntent().getStringExtra("nrocuenta");

        txtSaldoTrans = findViewById(R.id.txtSaldoTrans);
        txtCuentaTrans = findViewById(R.id.txtCuentaTrans);
        etCuentaDestino = findViewById(R.id.etCuentaDestino);
        etCantidad = findViewById(R.id.etCantidad);
        btnEnviar = findViewById(R.id.btnEnviar);

        txtSaldoTrans.setText(saldoTrans);
        txtCuentaTrans.setText(nrocuentaTrans);

        Toast.makeText(this, nrocuentaTrans, Toast.LENGTH_SHORT).show();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verificarSaldo();


            }
        });
    }
    private void enviarDinero(String url){
        //Toast.makeText(this, "envia dinero", Toast.LENGTH_SHORT).s
        // how();

        StringRequest rqEnviar = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(EnviarActivity.this, response, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Transacción exitosa.", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(EnviarActivity.this, "Error al enviar el dinero", Toast.LENGTH_SHORT).show();
                //Toast.makeText(EnviarActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            // Pasa los datos en formato JSON a la BD
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("nrocuentaDestino",etCuentaDestino.getText().toString());
                parametros.put("saldoEnviado",etCantidad.getText().toString());
                parametros.put("nrocuenta",txtCuentaTrans.getText().toString());

                Intent intencion = new Intent(getApplicationContext(),InicioActivity.class);
                intencion.putExtra("nombre", getIntent().getStringExtra("nombre"));
                intencion.putExtra("ident", getIntent().getStringExtra("ident"));
                intencion.putExtra("email", getIntent().getStringExtra("email"));
                intencion.putExtra("clave", getIntent().getStringExtra("clave"));

                startActivity(intencion);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqEnviar);
    }

    private void verificarSaldo(){
        if (Integer.parseInt(etCantidad.getText().toString()) > 0 && Integer.parseInt(saldoTrans) > 0){
            if (Integer.parseInt(saldoTrans) >= Integer.parseInt(etCantidad.getText().toString())){
                //Toast.makeText(this, "Si alcanza el saldo", Toast.LENGTH_SHORT).show();
                existeCuenta("http://192.168.1.57:90/bancoweb/existecuenta.php");
            } else {
                Toast.makeText(this, "No tiene saldo suficiente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe ingresar Cuenta destino y Cantidad a transferir.", Toast.LENGTH_LONG).show();
        }

    }

    private void existeCuenta(String url){

        StringRequest rqSaldo = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "si existe", Toast.LENGTH_LONG).show();
                //Toast.makeText(EnviarActivity.this, response, Toast.LENGTH_SHORT).show();
                enviarDinero("http://192.168.1.57:90/bancoweb/enviardinero.php");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "La cuenta destino no existe", Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            // Pasa los datos en formato JSON a la BD
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("nrocuenta",etCuentaDestino.getText().toString());
                //parametros.put("saldo",saldoTrans);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(rqSaldo);
    }
}
