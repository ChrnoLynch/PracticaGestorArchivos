package com.example.lprub.gestorarchivos.adaptador;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lprub on 10/11/2015.
 */
public class GestorArchivo {

    public GestorArchivo(){
    }

    public List<File> getArchivos(String ruta) {
        File archivo = new File(ruta);
        File lista[] = archivo.listFiles();
        List<File> archivos = new ArrayList<>();
        if(lista!=null) {
            for (File fichero : lista) {
                archivos.add(fichero);
            }
            Collections.sort(archivos, getComparador());
        }
        return archivos;
    }

    public Comparator<File> getComparador() {
        Comparator<File> c = new Comparator<File>() {
            @Override
            public int compare(File ar1, File ar2) {
                if (ar1.isDirectory() && ar2.isFile())
                    return -1;
                if ((ar2.isDirectory() && ar1.isFile()))
                    return 1;

                return ar1.getName().compareTo(ar2.getName());
            }

        };
        return c;
    }
}
