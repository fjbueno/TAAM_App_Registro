package com.example.proyectofinal_registro;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomContentProvider extends ContentProvider {
    public static final int UBICACION = 100;
    public static final int UBICACION_ID = 101;

    public DBAdapter dbAdapter;
    public static final UriMatcher URI_MATCHER = buildURIMatcher();

    public static UriMatcher buildURIMatcher(){
        String content = ContractUbicaciones.CONTENT_AUTORITY;
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(content, ContractUbicaciones.PATH_UBICACIONES, UBICACION);
        uriMatcher.addURI(content, ContractUbicaciones.PATH_UBICACIONES + "/#", UBICACION_ID);
        return uriMatcher;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri){
        switch (URI_MATCHER.match(uri)){
            case UBICACION:
                return ContractUbicaciones.EntryU.CONTENT_TYPE;
            case UBICACION_ID:
                return ContractUbicaciones.EntryU.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Error URI");
        }
    }

    @Override
    public boolean onCreate(){
        this.dbAdapter = new DBAdapter(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
        this.dbAdapter.open();
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case UBICACION:
                cursor = this.dbAdapter.busquedaUbicaciones();
                break;
            case UBICACION_ID:
                long _id = ContentUris.parseId(uri);
                cursor = this.dbAdapter.IdUbicacion((int)_id);
                break;
            default:
                throw new UnsupportedOperationException("Error URI");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues){
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs){
        this.dbAdapter.open();
        int rows = 0;

        switch (URI_MATCHER.match(uri))
        {
            case UBICACION:
                break;
            case UBICACION_ID:
                if (this.dbAdapter.eliminarUbicacion((int)ContentUris.parseId(uri))){
                    rows = 1;
                }
                break;
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs){
        return 0;
    }
}
