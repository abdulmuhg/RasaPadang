package personalproject.abdulmughniaf.rasapadang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class MainActivity extends AppCompatActivity {
    private DBHelper db;
    public String msg;
    public Bundle bundle;
    private RecyclerView listProduk;
    private ArrayList<ModelProduk> produk;
    public ArrayList<ModelTransaksi> transaksi;
    public ArrayList<String> pesanan = new ArrayList<>();

    public String nama_produk;
    public int jumlah_item, kode_produk;
    public Double harga_pokok, harga_jual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBHelper(this);

        inisialisasi();
        load_fungsi();
        load_data();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));


        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
                intent.putExtra("transaksiModels", transaksi);
                startActivity(intent);
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            kode_produk = intent.getIntExtra("kode_produk", 1);
            nama_produk = intent.getStringExtra("nama_produk");
            jumlah_item = intent.getIntExtra("jumlah_item",1);
            harga_pokok = intent.getDoubleExtra("harga_pokok_produk",0);
            harga_jual = intent.getDoubleExtra("harga_produk",0);

            if(jumlah_item < 1){
                db.deleteRow(kode_produk);
            }
//            ModelTransaksi modelTrans = new ModelTransaksi();
//            modelTrans.setNamaProduk(nama_produk);
//            modelTrans.setJumlahItem(jumlah_item);
//            transaksi.add(modelTrans);

            String queryCek = "SELECT * FROM keranjang WHERE kode_produk = "+ kode_produk;
            if(db.customQuery(queryCek).getCount() > 0){
                //jika sudah ada
                db.updateKeranjang(kode_produk, jumlah_item);
                Toast.makeText(MainActivity.this,"" + jumlah_item,Toast.LENGTH_SHORT).show();
            }else{
                //jika belum ada
                //lakukan proses insert jika data sudah lengkap
                if(db.masukkanKeranjang(
                        //konversi nilai dari text ke tipe data sesuai dengan
                        //yang ada di parameter Method insertProduk pada class DBHelper
                        Integer.valueOf(kode_produk),
                        String.valueOf(nama_produk),
                        Double.valueOf(harga_jual),
                        Integer.valueOf(jumlah_item)
                ) && jumlah_item > 0){
                    //beritahukan jika input berhasil dengan TOASTER
                    Toast.makeText(MainActivity.this,"Data berhasil ditambahkan.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void inisialisasi() {
        transaksi = new ArrayList<>();
        transaksi.clear();
        listProduk = findViewById(R.id.listProduk);

    }

    private void load_fungsi(){}

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
            do {
                ModelProduk model = new ModelProduk();
                model.setKode_produk(cursor.getInt(cursor.getColumnIndex("kode_produk")));
                model.setNama_produk(cursor.getString(cursor.getColumnIndex("nama_produk")));
                model.setHarga_pokok(cursor.getDouble(cursor.getColumnIndex("harga_pokok")));
                model.setHarga_jual(cursor.getDouble(cursor.getColumnIndex("harga_jual")));
                model.setStok(cursor.getInt(cursor.getColumnIndex("stok")));
                produk.add(model);
                //cursor.moveToNext();
            } while (cursor.moveToNext());
            //mempersiapkan adapter untuk recyclerView
            AdapterProdukUser adapterProduk = new AdapterProdukUser(this, produk);
            //mengatur adapter untuk RecyclerView
            listProduk.setAdapter(adapterProduk);
            listProduk.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ListProdukActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
