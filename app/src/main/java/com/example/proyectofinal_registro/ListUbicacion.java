package com.example.proyectofinal_registro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListUbicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listubicacion);
        listUbicaciones();
    }

    public ArrayList<Ubicacion> listUbicaciones = new ArrayList<Ubicacion>();

    public void listUbicaciones(){
        this.listUbicaciones.clear();
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.busquedaUbicaciones();
        if(cursor.moveToFirst()){
            do{
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.set_id(cursor.getInt(0));
                ubicacion.setNombre(cursor.getString(1));
                ubicacion.setDescripcion(cursor.getString(2));
                ubicacion.setLatitud(cursor.getDouble(3));
                ubicacion.setLongitud(cursor.getDouble(4));
                listUbicaciones.add(ubicacion);
            } while (cursor.moveToNext());
        }
        dbAdapter.close();

        AdapterU adapterU = new AdapterU(listUbicaciones, this);

        ListView listView = findViewById(R.id.listUbicaciones);
        listView.setAdapter(adapterU);

    }

    public void onResume(){
        super.onResume();
        listUbicaciones();
    }

    public void ModificarUbicaciones(Integer id)
    {
        Intent i = new Intent(this, AddUbicacion.class);
        i.putExtra("id", id);
        startActivity(i);
        listUbicaciones();
    }
}
