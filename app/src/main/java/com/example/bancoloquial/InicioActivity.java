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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InicioActivity extends AppCompatActivity {
Button eliminar,editar,ingresar;
TextView tvBienvenido;
String i,n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        eliminar=findViewById ( R.id.btneliminar );
        editar=findViewById ( R.id.btnEditar );
        ingresar=findViewById ( R.id.Ingresar );

        i=getIntent ().getStringExtra ( "ident" );
        n=getIntent ().getStringExtra ( "nombre" );


        tvBienvenido=findViewById ( R.id.textView5 );
        tvBienvenido.setText( n );


        eliminar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                ServicioEliminar ( "http:172.16.22.19:8087/Volley/elimina.php" );
            }
        } );
        ingresar.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Intent l= new Intent ( getApplicationContext (),TransaActivity.class );
                l.putExtra ( "ident",i );
                startActivity ( l );
            }
        } );
    }
    public void ingreasr() {
        Intent in=new Intent(getApplicationContext (),InicioActivity.class);
        startActivity ( in );
    }
    private void ServicioEliminar(String URL) {
        StringRequest stringRequest = new StringRequest ( Request.Method.POST , URL , new Response.Listener<String> ( ) {
            @Override
            public void onResponse ( String response ) {
                Toast.makeText ( getApplicationContext (),response,Toast.LENGTH_SHORT ).show ();
            }
        } , new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Toast.makeText ( getApplicationContext ( ) , error.toString ( ) , Toast.LENGTH_SHORT ).show ( );
            }
        } ) {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String> ( );
                parametros.put ( "ident" , i );
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue ( this );
        requestQueue.add ( stringRequest );
    }
}
