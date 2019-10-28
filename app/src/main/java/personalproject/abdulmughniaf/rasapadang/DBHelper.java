package personalproject.abdulmughniaf.rasapadang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "penjualan";

    private String BUAT_TABEL_PRODUK = "CREATE TABLE IF NOT EXISTS produk (" +
            "kode_produk INTEGER PRIMARY KEY," +
            "nama_produk TEXT," +
            "harga_pokok REAL," +
            "harga_jual REAL," +
            "stok INTEGER)";

    private String BUAT_TABEL_TRANSAKSI = "CREATE TABLE IF NOT EXISTS transaksi (" +
            "kode_transaksi INTEGER PRIMARY KEY," +
            "tanggal TEXT," +
            "total REAL," +
            "bayar REAL," +
            "kembalian REAL)";

    private String BUAT_TABEL_DETAIL_TRANSAKSI = "CREATE TABLE IF NOT EXISTS detail_transaksi (" +
            "kode_detail INTEGER PRIMARY KEY," +
            "kode_transaksi INTEGER," +
            "kode_produk INTEGER," +
            "qty INTEGER)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUAT_TABEL_PRODUK);
        db.execSQL(BUAT_TABEL_TRANSAKSI);
        db.execSQL(BUAT_TABEL_DETAIL_TRANSAKSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    Cursor customQuery(String query){
        Cursor cursor = getWritableDatabase().rawQuery(query,null);
        cursor.moveToFirst();
        return cursor;
    }

    Boolean insertProduk(int kode_produk,
                         String nama_produk,
                         Double harga_jual,
                         Double harga_pokok,
                         int stok)
    {
        ContentValues values = new ContentValues();
        values.put("kode_produk",kode_produk);
        values.put("nama_produk",nama_produk);
        values.put("harga_jual",harga_jual);
        values.put("harga_pokok",harga_pokok);
        values.put("stok",stok);
        getWritableDatabase().insert("produk",null,values);
        return true;
    }

    Boolean insertTransaksi(int kode_transaksi,
                            String tanggal,
                            Double total,
                            Double bayar,
                            Double kembalian){
        ContentValues values = new ContentValues();
        values.put("kode_transaksi",kode_transaksi);
        values.put("tanggal",tanggal);
        values.put("total",total);
        values.put("bayar",bayar);
        values.put("kembalian",kembalian);
        getWritableDatabase().insert("transaksi",null,values);
        return true;
    }

    Boolean insertDetailTransaksi(int kode_detail,
                                  int kode_transaksi,
                                  int kode_produk,
                                  int qty){
        ContentValues values = new ContentValues();
        values.put("kode_transaksi",kode_transaksi);
        values.put("kode_detail",kode_detail);
        values.put("kode_produk",kode_produk);
        values.put("qty",qty);
        getWritableDatabase().insert("detail_transaksi",null,values);
        return true;
    }
}