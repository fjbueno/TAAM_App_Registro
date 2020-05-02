package com.example.proyectofinal_registro;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractUbicaciones {
    public static final String CONTENT_AUTORITY = "com.example.proyectofinal_registro";
    public static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTORITY);
    public static final String PATH_UBICACIONES = "Ubicaciones";

    public static final class EntryU implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon().appendPath(PATH_UBICACIONES).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_UBICACIONES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_UBICACIONES;

        public static Uri Uuri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
