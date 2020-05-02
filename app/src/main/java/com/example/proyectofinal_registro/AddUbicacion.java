package com.example.proyectofinal_registro;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUbicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addubicacion);

        if(getIntent().hasExtra("id")){
            Integer id = getIntent().getExtras().getInt("id");
            UbicacionesV(id);
        }
    }

    Ubicacion ubicacion = null;

    public void UbicacionesV(Integer id){
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.IdUbicacion(id);
        if (cursor.moveToFirst()){
            this.ubicacion = new Ubicacion();
            ubicacion.set_id(cursor.getInt(0));
            ubicacion.setNombre(cursor.getString(1));
            ubicacion.setDescripcion(cursor.getString(2));
            ubicacion.setLatitud(cursor.getDouble(3));
            ubicacion.setLongitud(cursor.getDouble(4));
        }
        dbAdapter.close();

        if(this.ubicacion !=null){
            EditText editText = findViewById(R.id.editText);
            editText.setText(this.ubicacion.getNombre());
            editText = findViewById(R.id.editText1);
            editText.setText(this.ubicacion.getDescripcion());
            editText = findViewById(R.id.editText2);
            editText.setText(this.ubicacion.getLatitud().toString());
            editText = findViewById(R.id.editText3);
            editText.setText(this.ubicacion.getLongitud().toString());
        }
    }

    public void onClickGuardar(View view) {
        if (this.ubicacion == null) {
            Ubicacion ubicacion = new Ubicacion();
            EditText editText = findViewById(R.id.editText);
            ubicacion.setNombre(editText.getText().toString());
            editText = findViewById(R.id.editText1);
            ubicacion.setDescripcion(editText.getText().toString());
            editText = findViewById(R.id.editText2);
            ubicacion.setLatitud(Double.parseDouble(editText.getText().toString()));
            editText = findViewById(R.id.editText3);
            ubicacion.setLongitud(Double.parseDouble(editText.getText().toString()));

            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.open();
            if (dbAdapter.addUbicacion(ubicacion) != -1) {
                Toast.makeText(getBaseContext(), "Nueva Ubicacion Correcta", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Error, Intente Nuevamente", Toast.LENGTH_LONG).show();
            }
            dbAdapter.close();
            finish();
        } else {
            EditText editText = findViewById(R.id.editText);
            this.ubicacion.setNombre(editText.getText().toString());
            editText = findViewById(R.id.editText1);
            this.ubicacion.setDescripcion(editText.getText().toString());
            editText = findViewById(R.id.editText2);
            this.ubicacion.setLatitud(Double.parseDouble(editText.getText().toString()));
            editText = findViewById(R.id.editText3);
            this.ubicacion.setLongitud(Double.parseDouble(editText.getText().toString()));

            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.open();
            if(dbAdapter.actualizacionUbicacion(this.ubicacion)){
                Toast.makeText(this, "Ubicacion Actualizada", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Error, Intente Nuevamente", Toast.LENGTH_LONG).show();
            }
            dbAdapter.close();
            finish();
        }
    }
    public void onClickCancelar(View view){
        finish();
    }

}
