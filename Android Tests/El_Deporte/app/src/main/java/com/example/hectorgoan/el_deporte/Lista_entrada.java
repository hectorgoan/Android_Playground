package com.example.hectorgoan.el_deporte;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by hectorgoan on 22/08/14.
 */
//ES LA CLASE QUE DEFINIRÁ LAS ENTRADAS (SUS ATRIBUTOS)
public class Lista_entrada
{
    //Declaración de variables para manejo de datos
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;

    //Obtención de datos
    public Lista_entrada(int idImagen, String textoEncima, String textoDebajo)
    {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }
    //getters
    public String get_TextoEncima()
    {
        return textoEncima;
    }
    public String get_TextoDebajo()
    {
        return textoDebajo;
    }
    public int get_idImagen()
    {
        return idImagen;
    }
}
