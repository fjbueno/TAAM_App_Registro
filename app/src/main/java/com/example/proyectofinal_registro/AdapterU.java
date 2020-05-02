package com.example.proyectofinal_registro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterU extends BaseAdapter implements ListAdapter {

    private ArrayList<Ubicacion> Ulist;
    private Context context;

    public AdapterU(ArrayList<Ubicacion> Ulist, Context context){
        this.Ulist = Ulist;
        this.context = context;
    }

    @Override
    public int getCount(){
        return Ulist.size();
    }

    @Override
    public Object getItem(int position){
        return Ulist.get(position);
    }

    @Override
    public long getItemId(int position){
        return Ulist.get(position).get_id();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){
        View viewU = view;
        if(viewU == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewU = layoutInflater.inflate(R.layout.listacontrolubicacion, null);
        }

        TextView textView = viewU.findViewById(R.id.ListUNombre);
        textView.setText(Ulist.get(position).getNombre());
        Button Eliminar = viewU.findViewById(R.id.EliminarBoton);
        Button Modificar = viewU.findViewById(R.id.ModificarBoton);

        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MUbicacion(Ulist.get(position).get_id());
            }
        });

        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EUbicacion(Ulist.get(position).get_id(), Ulist.get(position).getNombre());
            }
        });
        return viewU;
    }

    private void MUbicacion(Integer id)
    {
        ((ListUbicacion)context).ModificarUbicaciones(id);
    }

    private void EUbicacion(final Integer id, String Ubicacion){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setCancelable(true)
                .setMessage("¿Seguro que desea eliminar la Ubicacion: " + Ubicacion + "?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eUbicacion(id);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void eUbicacion(Integer id){
        DBAdapter dbAdapter = new DBAdapter(this.context);
        dbAdapter.open();
        if (dbAdapter.eliminarUbicacion(id))
        {
            Toast.makeText(context, "Ubicacion Eliminada con Exito", Toast.LENGTH_LONG).show();
            ((ListUbicacion)context).onResume();
        }else {
            Toast.makeText(context, "Error, Intentar Nuevamente", Toast.LENGTH_LONG).show();
        }
    }
}
