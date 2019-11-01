package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TransaksiActivity extends AppCompatActivity {
    private RecyclerView listTrans;
    public Button btnBayar, btnSelesai;
    public EditText etTotalBayar;
    public TextView tvKembalian;

    private DBHelper db;
    public ArrayList<ModelTransaksi> transaksiModel, transaksiModelLama;
    public double totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        db = new DBHelper(this);
        transaksiModel = new ArrayList<>();
        transaksiModel.clear();
        transaksiModelLama = (ArrayList<ModelTransaksi>) getIntent().getSerializableExtra("transaksiModels");
        listTrans = findViewById(R.id.listTransaksi);

        btnBayar = findViewById(R.id.btnBayar);
        etTotalBayar = findViewById(R.id.etTotalBayar);
        tvKembalian = findViewById(R.id.tvKembalian);
        btnSelesai = findViewById(R.id.btnSelesai);

        listTrans.setLayoutManager(new LinearLayoutManager(this));

        String query = "SELECT * FROM keranjang WHERE jumlah_produk > 0";
        //mengeksekusi custom query
        Cursor cursor = db.customQuery(query);
        //mengarahkan cursor pada baris pertama hasil query
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ModelTransaksi model = new ModelTransaksi();
                model.setNamaProduk(cursor.getString(cursor.getColumnIndex("nama_produk")));
                model.setHargaJual(cursor.getDouble(cursor.getColumnIndex("harga_jual")));
                model.setJumlahItem(cursor.getInt(cursor.getColumnIndex("jumlah_produk")));
                transaksiModel.add(model);

                double harga = cursor.getDouble(cursor.getColumnIndex("harga_jual"));
                int qty = cursor.getInt(cursor.getColumnIndex("jumlah_produk"));
                totalHarga = totalHarga + (harga * qty);
                btnBayar.setText("Bayar : Rp " + totalHarga);
                //cursor.moveToNext();
            } while (cursor.moveToNext());

            AdapterTransaksi adapterTransaksi = new AdapterTransaksi(this, transaksiModel);
            listTrans.setAdapter(adapterTransaksi);
            listTrans.getAdapter().notifyDataSetChanged();
        }

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTotalBayar.getText().toString().isEmpty()) {
                    Toast.makeText(TransaksiActivity.this, "Masukkan Uang Pembeli", Toast.LENGTH_SHORT).show();
                }
                else {
                    double uangPembeli = Double.valueOf(etTotalBayar.getText().toString());
                    double kembalian = uangPembeli - totalHarga;

                    if (kembalian < 0) {
                        Snackbar snackbar = Snackbar.make(view, "Uang Tidak Cukup", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(view, "Uang kembalian : Rp. " + kembalian, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        tvKembalian.setText("Uang Kembalian : " + kembalian);
                        //startActivity(intent);
                    }
                }
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvKembalian.getText().toString().isEmpty()){
                    Toast.makeText(TransaksiActivity.this, "Mohon Selesaikan Pembayaran", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(TransaksiActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
