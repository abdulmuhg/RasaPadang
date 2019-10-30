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

public class MainActivity extends AppCompatActivity {
    private DBHelper db;
    public String msg;
    private RecyclerView listProduk;
    private ArrayList<ModelProduk> produk;
    public ArrayList<String> pesanan = new ArrayList<>();
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
        pesanan.add("dummy");
        FloatingActionButton fab = findViewById(R.id.fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
                intent.putExtra("nama_produk",pesanan);
                startActivity(intent);
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String nama_produk = intent.getStringExtra("nama_produk");
            Toast.makeText(MainActivity.this,nama_produk ,Toast.LENGTH_SHORT).show();
            msg = nama_produk;
            pesanan.add(msg);
        }
    };

    private void inisialisasi() {
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
        while (cursor.moveToNext()){
            ModelProduk model = new ModelProduk();
            model.setKode_produk(cursor.getInt(cursor.getColumnIndex("kode_produk")));
            model.setNama_produk(cursor.getString(cursor.getColumnIndex("nama_produk")));
            model.setHarga_pokok(cursor.getDouble(cursor.getColumnIndex("harga_pokok")));
            model.setHarga_jual(cursor.getDouble(cursor.getColumnIndex("harga_jual")));
            model.setStok(cursor.getInt(cursor.getColumnIndex("stok")));
            produk.add(model);
            //cursor.moveToNext();
        }
        //mempersiapkan adapter untuk recyclerView
        AdapterProdukUser adapterProduk = new AdapterProdukUser(this,produk);
        //mengatur adapter untuk RecyclerView
        listProduk.setAdapter(adapterProduk);
        listProduk.getAdapter().notifyDataSetChanged();
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
