package com.example.bancoloquial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bancoloquial.Adapter.TransaccionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransaccionesActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    RecyclerView recyclerTransacciones;
    ArrayList<Transacciones> transac;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        transac= new ArrayList<>();
    recyclerTransacciones= findViewById(R.id.idRecycler);
    recyclerTransacciones.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    recyclerTransacciones.setHasFixedSize(true);

    request = Volley.newRequestQueue(getApplicationContext());
    cargarWebService();
    }

    private void cargarWebService() {

       String url = "http://192.168.1.57:90/bancoweb/ConsultarTransacciones.php";

       jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
       request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se puede conectar"+ error.toString(),Toast.LENGTH_SHORT).show();
        System.out.println();
        Log.d("Error", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Transacciones transaccion= null;

        JSONArray json = response.optJSONArray("transaccion");
        try {
        for (int i =0; i< json.length();i++) {
            transaccion = new Transacciones();
            JSONObject jsonObject = null;
            jsonObject = json.getJSONObject(i);
            transaccion.setNrocuenta(jsonObject.optString("nrocuentaorigen"));
            transaccion.setNrodestino(jsonObject.optString("nrocuentadestino"));
            transaccion.setFecha(jsonObject.optString("fecha"));
            transaccion.setValor(jsonObject.optString("valor"));
            transac.add(transaccion);
        }

            TransaccionAdapter adapter = new TransaccionAdapter(transac);
            recyclerTransacciones.setAdapter(adapter);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
