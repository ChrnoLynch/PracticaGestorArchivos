package com.example.lprub.gestorarchivos.actividad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lprub.gestorarchivos.R;

public class ActividadLector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_lector);
        TextView tvArchivo=(TextView) findViewById(R.id.tvArchivo);
        TextView tvTexto=(TextView) findViewById(R.id.tvTexto);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        tvArchivo.setText(bundle.getString("name"));
        tvTexto.setText(bundle.getString("texto"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_lector, menu);
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
}
