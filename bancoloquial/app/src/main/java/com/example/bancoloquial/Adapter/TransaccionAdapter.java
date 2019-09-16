package com.example.bancoloquial.Adapter;

import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancoloquial.R;
import com.example.bancoloquial.Transacciones;

import java.util.List;

public class TransaccionAdapter extends RecyclerView.Adapter<TransaccionAdapter.TransaccionHolder> {

    List<Transacciones> lista;
    public TransaccionAdapter(List<Transacciones> lista){
        this.lista=lista;
    }
    @Override
    public TransaccionHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaccion_list,parent,false);
        RecyclerView.LayoutParams layoutParams= new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new TransaccionHolder(vista);
    }

    @Override
    public void onBindViewHolder( TransaccionHolder holder, int position) {
            holder.txtCuentaOrigen.setText(lista.get(position).getNrocuenta().toString());
            holder.txtCuentaDestino.setText(lista.get(position).getNrodestino().toString());
            holder.txtFecha.setText(lista.get(position).getFecha().toString());
            holder.txtValor.setText(lista.get(position).getValor().toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class TransaccionHolder extends RecyclerView.ViewHolder{
        TextView txtCuentaOrigen,txtCuentaDestino,txtFecha,txtValor;

        public TransaccionHolder(View itemView){
            super(itemView);
            txtCuentaOrigen=itemView.findViewById(R.id.txtCuentaOrigen);
            txtCuentaDestino=itemView.findViewById(R.id.txtCuentaDestino);
            txtFecha=itemView.findViewById(R.id.txtFecha);
            txtValor=itemView.findViewById(R.id.txtValor);
        }

    }
}
