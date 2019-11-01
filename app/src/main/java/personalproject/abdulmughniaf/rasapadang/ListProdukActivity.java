package personalproject.abdulmughniaf.rasapadang;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListProdukActivity extends AppCompatActivity {
    private DBHelper db;

    private RecyclerView listProduk;
    private ArrayList<ModelProduk> produk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);
        db = new DBHelper(this);

        inisialisasi();
        load_fungsi();
        load_data();

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListProdukActivity.this, AddProdukActivity.class));
            }
        });
    }

    private void load_data() {
        //mempersiapkan list
        produk = new ArrayList<>();
        //memastikan list agar kosong
        produk.clear();
        //mengatur layoutmanager RecyclerView
        //agar menggunakan LinearLayout sebagai container
        listProduk.setLayoutManager(new LinearLayoutManager(this));

        String query = "SELECT * FROM produk";
        //mengeksekusi custom query
        Cursor cursor = db.customQuery(query);
        //mengarahkan cursor pada baris pertama hasil query
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do{
                ModelProduk model = new ModelProduk();
                model.setKode_produk(cursor.getInt(cursor.getColumnIndex("kode_produk")));
                model.setNama_produk(cursor.getString(cursor.getColumnIndex("nama_produk")));
                model.setHarga_pokok(cursor.getDouble(cursor.getColumnIndex("harga_pokok")));
                model.setHarga_jual(cursor.getDouble(cursor.getColumnIndex("harga_jual")));
                model.setStok(cursor.getInt(cursor.getColumnIndex("stok")));
                produk.add(model);
                //cursor.moveToNext();
            }while (cursor.moveToNext());
            //mempersiapkan adapter untuk recyclerView
            AdapterProduk adapterProduk = new AdapterProduk(this, produk);
            //mengatur adapter untuk RecyclerView
            listProduk.setAdapter(adapterProduk);
            listProduk.getAdapter().notifyDataSetChanged();
        }
    }

    private void inisialisasi() {
        listProduk = findViewById(R.id.listProduk);
    }

    private void load_fungsi(){

    }
}
