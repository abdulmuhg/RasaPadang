package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LabaActivity extends AppCompatActivity {
    private DBHelper db;
    private double nTotalLaba;

    private Button btnKembali;
    private RecyclerView listProduk;
    private TextView tvtotalLaba;
    private ArrayList<ModelLaba> produk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laba);
        db = new DBHelper(this);

        inisialisasi();
        load_data();

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LabaActivity.this, MainActivity.class);
                startActivity(i);
                clearTabelKeranjang();
            }
        });
    }

    private void clearTabelKeranjang(){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("keranjang", null, null);
    }

    private void load_data() {
        //mempersiapkan list
        produk = new ArrayList<>();
        //memastikan list agar kosong
        produk.clear();
        //mengatur layoutmanager RecyclerView
        //agar menggunakan LinearLayout sebagai container
        listProduk.setLayoutManager(new LinearLayoutManager(this));

        String query = "SELECT * FROM total_keuntungan";
        //mengeksekusi custom query
        Cursor cursor = db.customQuery(query);
        //mengarahkan cursor pada baris pertama hasil query
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do{
                ModelLaba model = new ModelLaba();
                model.setKode_trans(cursor.getString(cursor.getColumnIndex("kode_transaksi")));
                model.setLaba(cursor.getDouble(cursor.getColumnIndex("keuntungan")));
                produk.add(model);

                double totLaba = cursor.getDouble(cursor.getColumnIndex("keuntungan"));
                nTotalLaba = nTotalLaba + totLaba;
                tvtotalLaba.setText("Total : " + nTotalLaba);
            }while (cursor.moveToNext());
            //mempersiapkan adapter untuk recyclerView
            AdapterLaba adapterLaba = new AdapterLaba(this, produk);
            //mengatur adapter untuk RecyclerView
            listProduk.setAdapter(adapterLaba);
            listProduk.getAdapter().notifyDataSetChanged();
        }
    }

    private void inisialisasi() {

        listProduk = findViewById(R.id.listKeuntungan);
        tvtotalLaba = findViewById(R.id.tvtotalLaba);
        btnKembali = findViewById(R.id.btnKembali);
    }
}
