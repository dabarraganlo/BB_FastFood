package com.dabl.bb_fastfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PedidoDAO {

    private final DBHelper dbHelper;

    public PedidoDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertarPedido(Pedido pedido) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_P_FECHA,        pedido.getFecha());
        values.put(DBHelper.COL_P_METODO_PAGO,  pedido.getMetodoPago());
        values.put(DBHelper.COL_P_TOTAL_PRECIO, pedido.getTotalPrecio());
        values.put(DBHelper.COL_P_TOTAL_PUNTOS, pedido.getTotalPuntos());
        values.put(DBHelper.COL_P_ESTADO,       pedido.getEstado());

        long pedidoId = db.insert(DBHelper.TABLE_PEDIDOS, null, values);

        for (Producto item : pedido.getItems()) {
            ContentValues iv = new ContentValues();
            iv.put(DBHelper.COL_I_PEDIDO_ID, pedidoId);
            iv.put(DBHelper.COL_I_NOMBRE,    item.getNombre());
            iv.put(DBHelper.COL_I_PRECIO,    item.getPrecio());
            iv.put(DBHelper.COL_I_PUNTOS,    item.getPuntos());
            iv.put(DBHelper.COL_I_CANTIDAD,  item.getCantidad());
            db.insert(DBHelper.TABLE_ITEMS, null, iv);
        }

        return pedidoId;
    }

    public ArrayList<Pedido> obtenerTodosLosPedidos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Pedido> lista = new ArrayList<>();

        Cursor cursor = db.query(DBHelper.TABLE_PEDIDOS, null, null, null, null, null,
                DBHelper.COL_P_ID + " DESC");

        while (cursor.moveToNext()) {
            Pedido p = construirPedidoDesdeCursor(cursor, db);
            lista.add(p);
        }
        cursor.close();
        return lista;
    }

    public Pedido obtenerPedidoPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_PEDIDOS, null,
                DBHelper.COL_P_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        Pedido p = construirPedidoDesdeCursor(cursor, db);
        cursor.close();
        return p;
    }

    public void actualizarEstado(int pedidoId, String nuevoEstado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_P_ESTADO, nuevoEstado);
        db.update(DBHelper.TABLE_PEDIDOS, values,
                DBHelper.COL_P_ID + " = ?", new String[]{String.valueOf(pedidoId)});
    }

    public void eliminarPedido(int pedidoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_ITEMS,
                DBHelper.COL_I_PEDIDO_ID + " = ?", new String[]{String.valueOf(pedidoId)});
        db.delete(DBHelper.TABLE_PEDIDOS,
                DBHelper.COL_P_ID + " = ?", new String[]{String.valueOf(pedidoId)});
    }

    private Pedido construirPedidoDesdeCursor(Cursor cursor, SQLiteDatabase db) {
        int id          = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_P_ID));
        String fecha    = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_P_FECHA));
        String metodo   = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_P_METODO_PAGO));
        int totalPrecio = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_P_TOTAL_PRECIO));
        int totalPuntos = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_P_TOTAL_PUNTOS));
        String estado   = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_P_ESTADO));

        ArrayList<Producto> items = new ArrayList<>();
        Cursor ic = db.query(DBHelper.TABLE_ITEMS, null,
                DBHelper.COL_I_PEDIDO_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (ic.moveToNext()) {
            Producto p = new Producto(
                    ic.getString(ic.getColumnIndexOrThrow(DBHelper.COL_I_NOMBRE)),
                    ic.getInt(ic.getColumnIndexOrThrow(DBHelper.COL_I_PRECIO)),
                    ic.getInt(ic.getColumnIndexOrThrow(DBHelper.COL_I_PUNTOS)),
                    0
            );
            p.setCantidad(ic.getInt(ic.getColumnIndexOrThrow(DBHelper.COL_I_CANTIDAD)));
            items.add(p);
        }
        ic.close();

        Pedido pedido = new Pedido(id, fecha, items, metodo, totalPrecio, totalPuntos);
        pedido.setEstado(estado);
        return pedido;
    }
}
