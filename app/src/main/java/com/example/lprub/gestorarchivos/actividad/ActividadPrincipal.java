package com.example.lprub.gestorarchivos.actividad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lprub.gestorarchivos.adaptador.Adaptador;
import com.example.lprub.gestorarchivos.adaptador.GestorArchivo;
import com.example.lprub.gestorarchivos.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ActividadPrincipal extends AppCompatActivity {

    private Adaptador clAdaptador;
    private TextView tvRuta;
    private ListView lv;
    private GestorArchivo gestor;
    private List<File> lista;
    private File padre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        tvRuta= (TextView) findViewById(R.id.tvRuta);
        gestor=new GestorArchivo();
        lista=gestor.getArchivos("/");
        tvRuta.setText(lista.get(0).getParentFile().getAbsolutePath());
        llamarAdaptador(lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
String s="";
    public void llamarAdaptador(final List<File> archivos){
        lv = (ListView) findViewById(R.id.lvGestor);
        //Crear objeto adaptador y ponerselo a nuestro listview
        clAdaptador = new Adaptador(this, R.layout.item, archivos);
        lv.setAdapter(clAdaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                if (lista.get(posicion).isDirectory()) {
                    File aux = lista.get(posicion);
                    lista = gestor.getArchivos(lista.get(posicion).getAbsolutePath());
                    try {
                        if (lista.size() > 0) {
                            padre = null;
                            padre = new File(lista.get(0).getParentFile().getCanonicalPath(), "../");
                            lista.add(0, padre);
                        } else {
                            padre = new File(aux.getParent(), ".");
                            lista.add(0, padre);
                        }
                        s = aux.getCanonicalPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    llamarAdaptador(lista);
                } else {
                    if (lista.get(posicion).isFile()&&lista.get(posicion).canExecute()){
                        String texto=lectura(lista.get(posicion));
                        String name=lista.get(posicion).getName();
                        Intent intent=new Intent(ActividadPrincipal.this, ActividadLector.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("texto", texto);
                        bundle.putString("name", name);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                if (lista.get(0).getAbsolutePath().equals("/..") && lista.size() != 1) {
                    lista.remove(0);
                    s = "/";
                }
                tvRuta.setText(s);
            }
        });
        //Registrar el control ListView para mostrar un menu contextual
        registerForContextMenu(lv);
    }

        public String lectura(File file){
            String p="";
            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String linea;
                while((linea=br.readLine())!=null){
                    p+=linea+"\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return p;
        }
}
