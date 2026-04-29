package com.dabl.bb_fastfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME    = "bb_fastfood.db";
    static final int    DB_VERSION = 1;

    static final String TABLE_PEDIDOS      = "pedidos";
    static final String COL_P_ID           = "id";
    static final String COL_P_FECHA        = "fecha";
    static final String COL_P_METODO_PAGO  = "metodo_pago";
    static final String COL_P_TOTAL_PRECIO = "total_precio";
    static final String COL_P_TOTAL_PUNTOS = "total_puntos";
    static final String COL_P_ESTADO       = "estado";

    static final String TABLE_ITEMS     = "pedido_items";
    static final String COL_I_ID        = "id";
    static final String COL_I_PEDIDO_ID = "pedido_id";
    static final String COL_I_NOMBRE    = "nombre";
    static final String COL_I_PRECIO    = "precio";
    static final String COL_I_PUNTOS    = "puntos";
    static final String COL_I_CANTIDAD  = "cantidad";

    private static final String CREATE_PEDIDOS =
        "CREATE TABLE " + TABLE_PEDIDOS + " (" +
            COL_P_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_P_FECHA        + " TEXT, " +
            COL_P_METODO_PAGO  + " TEXT, " +
            COL_P_TOTAL_PRECIO + " INTEGER, " +
            COL_P_TOTAL_PUNTOS + " INTEGER, " +
            COL_P_ESTADO       + " TEXT)";

    private static final String CREATE_ITEMS =
        "CREATE TABLE " + TABLE_ITEMS + " (" +
            COL_I_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_I_PEDIDO_ID + " INTEGER, " +
            COL_I_NOMBRE    + " TEXT, " +
            COL_I_PRECIO    + " INTEGER, " +
            COL_I_PUNTOS    + " INTEGER, " +
            COL_I_CANTIDAD  + " INTEGER, " +
            "FOREIGN KEY(" + COL_I_PEDIDO_ID + ") REFERENCES " +
                TABLE_PEDIDOS + "(" + COL_P_ID + "))";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PEDIDOS);
        db.execSQL(CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);
        onCreate(db);
    }
}
