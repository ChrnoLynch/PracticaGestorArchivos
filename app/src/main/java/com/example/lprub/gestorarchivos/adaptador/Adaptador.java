package com.example.lprub.gestorarchivos.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lprub.gestorarchivos.R;
import com.example.lprub.gestorarchivos.actividad.ActividadPrincipal;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lprub on 10/11/2015.
 */
public class Adaptador extends ArrayAdapter<File>{

    private Context context;
    private int resource;
    private List<File> archivos;
    public Adaptador(Context context, int resource, List<File> archivos) {
        super(context, resource,archivos);
        this.context=context;
        this.resource=resource;
        this.archivos=archivos;
    }

    static class SalvadorReferencia{
        public ImageView ivIcon;
        public TextView tvName,tvDate,tvPermisos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater i=(LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        SalvadorReferencia sr=new SalvadorReferencia();
        if(convertView==null){
            convertView = i.inflate(resource,null);
            sr.tvName=(TextView)convertView.findViewById(R.id.tvName);
            sr.tvDate=(TextView)convertView.findViewById(R.id.tvDate);
            sr.tvPermisos=(TextView)convertView.findViewById(R.id.tvPermisos);
            sr.ivIcon=(ImageView)convertView.findViewById(R.id.ivIcon);
            convertView.setTag(sr);
        }else{
            sr=(SalvadorReferencia)convertView.getTag();
        }
            sr.tvName.setText(archivos.get(position).getName());
             if (archivos.get(position).isDirectory()) {
                sr.ivIcon.setImageResource(R.drawable.folder);
            } else {
                sr.ivIcon.setImageResource(R.drawable.fileoutline);
             }
                    GregorianCalendar gr = new GregorianCalendar();
                    gr.setTimeInMillis(archivos.get(position).lastModified());
                    String fecha = "" + gr.get(Calendar.DAY_OF_MONTH) + "/" + (gr.get(Calendar.MONTH)
                            + 1) + "/" + gr.get(Calendar.YEAR)
                            + "   " + gr.get(Calendar.HOUR_OF_DAY)
                            + ":" + gr.get(Calendar.MINUTE);
                    sr.tvDate.setText(fecha);
                    String permisos = "";
                    if (archivos.get(position).canRead())
                        permisos = permisos + "r";
                    if (archivos.get(position).canWrite())
                        permisos = permisos + "w";
                    if (archivos.get(position).canExecute())
                        permisos = permisos + "x";
                    sr.tvPermisos.setText(permisos);
        return convertView;
    }
}
