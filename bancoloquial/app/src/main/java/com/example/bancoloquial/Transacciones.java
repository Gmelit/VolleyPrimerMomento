package com.example.bancoloquial;

import java.util.Date;

public class Transacciones {
    private String nrocuenta;
    private String nrodestino;
    private String fecha;
    private String valor;


    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public String getNrodestino() {
        return nrodestino;
    }

    public void setNrodestino(String nrodestino) {
        this.nrodestino = nrodestino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
