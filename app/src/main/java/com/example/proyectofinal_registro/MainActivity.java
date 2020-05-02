package com.example.proyectofinal_registro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRegistrar(View view){
        startActivity(new Intent(this, AddUbicacion.class));
    }

    public void onClickBorrar(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true)
                .setMessage("¿Esta seguro de eliminar las ubicaciones?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(); }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickVisualizar(View view){
        startActivity(new Intent(this, ListUbicacion.class));
    }

    public void eliminar(){
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        if (dbAdapter.borrarUbicaciones() > 0) {
            Toast.makeText(this, "Ubicaciones Eliminadas", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error, Intentar Nuevamente", Toast.LENGTH_LONG).show();
        }
        dbAdapter.close();
    }
}
