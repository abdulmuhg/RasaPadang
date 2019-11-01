package personalproject.abdulmughniaf.rasapadang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "penjualan";

    public static final String TABLE_USERS = "users";

    public static final String KEY_ID = "id";

    public static final String KEY_USER_NAME = "username";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";

    public String SQL_TABLE_USERS = " CREATE TABLE IF NOT EXISTS " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

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

    private String BUAT_TABEL_KERANJANG = "CREATE TABLE IF NOT EXISTS keranjang (" +
            "kode_produk INTEGER PRIMARY KEY," +
            "nama_produk TEXT," +
            "harga_jual REAL," +
            "harga_pokok REAL," +
            "jumlah_produk INTEGER)";

    private String BUAT_TABEL_DETAIL_KEUNTUNGAN = "CREATE TABLE IF NOT EXISTS total_keuntungan (" +
            "kode_transaksi TEXT PRIMARY KEY," +
            "keuntungan REAL)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUAT_TABEL_PRODUK);
        db.execSQL(BUAT_TABEL_TRANSAKSI);
        db.execSQL(BUAT_TABEL_DETAIL_TRANSAKSI);
        db.execSQL(SQL_TABLE_USERS);
        db.execSQL(BUAT_TABEL_KERANJANG);
        db.execSQL(BUAT_TABEL_DETAIL_KEUNTUNGAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
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

    Boolean insertKeuntungan(String kode_transaksi,
                                  double laba){
        ContentValues values = new ContentValues();
        values.put("kode_transaksi",kode_transaksi);
        values.put("keuntungan",laba);
        getWritableDatabase().insert("total_keuntungan",null,values);
        return true;
    }

    Boolean masukkanKeranjang(int kode_produk,
                              String nama_produk,
                              double harga_produk,
                              double harga_pokok,
                              int jumlah
                              ){
        ContentValues values = new ContentValues();
        values.put("kode_produk",kode_produk);
        values.put("nama_produk", nama_produk);
        values.put("harga_jual", harga_produk);
        values.put("harga_pokok", harga_pokok);
        values.put("jumlah_produk",jumlah);
        getWritableDatabase().insert("keranjang",null,values);
        return true;
    }
    public boolean updateKeranjang(int kode, int newVal)
    {
        ContentValues args = new ContentValues();
        args.put("jumlah_produk", newVal);
        getWritableDatabase().update("keranjang", args, "kode_produk="+kode, null);
        return true;
    }

    public void deleteRow(int kode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "keranjang" + " WHERE "+ "kode_produk" +"='"+kode+"'");
        db.close();
    }


    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}