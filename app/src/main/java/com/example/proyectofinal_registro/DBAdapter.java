package com.example.proyectofinal_registro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    public static final String KEY_ROWID = "_id";
    static final String KEY_NOMBRE = "Nombre";
    static final String KEY_DESCRIPCION = "Descripcion";
    static final String KEY_LATITUD = "Latitud";
    static final String KEY_LONGITUD = "Longitud";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "BDUbicaciones";
    static final String DATABASE_TABLE = "Ubicacion";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " integer primary key autoincrement, " +
                    KEY_NOMBRE + " text not null, " +
                    KEY_DESCRIPCION + " text not null, " +
                    KEY_LATITUD + " real not null, " +
                    KEY_LONGITUD + " real not null);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase DB;

    public DBAdapter(Context context){
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase DB){
            try{
                DB.execSQL(DATABASE_CREATE);
            }catch (SQLException Exception){
                Exception.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.v(TAG, "Actualizando BD...");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException{
        DB = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long addUbicacion(Ubicacion ubicacion){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOMBRE, ubicacion.getNombre());
        contentValues.put(KEY_DESCRIPCION, ubicacion.getDescripcion());
        contentValues.put(KEY_LATITUD, ubicacion.getLatitud());
        contentValues.put(KEY_LONGITUD, ubicacion.getLongitud());
        return DB.insert(DATABASE_TABLE, null, contentValues);
    }

    public int borrarUbicaciones(){
        return DB.delete(DATABASE_TABLE, null, null);
    }

    public Cursor busquedaUbicaciones(){
        return DB.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NOMBRE, KEY_DESCRIPCION, KEY_LATITUD, KEY_LONGITUD}, null, null, null, null, null);
    }

    public Cursor IdUbicacion(Integer _id){
        return DB.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NOMBRE, KEY_DESCRIPCION, KEY_LATITUD, KEY_LONGITUD},KEY_ROWID + "=" + _id, null, null, null, null);
    }

    public boolean actualizacionUbicacion(Ubicacion ubicacion){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOMBRE, ubicacion.getNombre());
        contentValues.put(KEY_DESCRIPCION, ubicacion.getDescripcion());
        contentValues.put(KEY_LATITUD, ubicacion.getLatitud());
        contentValues.put(KEY_LONGITUD, ubicacion.getLongitud());
        return DB.update(DATABASE_TABLE, contentValues, KEY_ROWID + "=" + ubicacion.get_id(), null) > 0;
    }

    public boolean eliminarUbicacion(Integer _id){
        return (DB.delete(DATABASE_TABLE, KEY_ROWID + "=" + _id, null) != 0);
    }

}
