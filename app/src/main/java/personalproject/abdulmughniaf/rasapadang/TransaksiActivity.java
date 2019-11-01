package personalproject.abdulmughniaf.rasapadang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Random;

public class TransaksiActivity extends AppCompatActivity {
    private RecyclerView listTrans;
    public Button btnBayar, btnSelesai;
    public EditText etTotalBayar;
    public TextView tvKembalian;

    private DBHelper db;
    public ArrayList<ModelTransaksi> transaksiModel, transaksiModelLama;
    public double totalHarga;
    public double totalKeuntungan;
    public String kode_transaksi;

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

                //hitung keuntungan per transaksi
                double modal = cursor.getDouble(cursor.getColumnIndex("harga_pokok"));
                double hargaJual = cursor.getDouble(cursor.getColumnIndex("harga_jual"));
                double keuntungan = (hargaJual - modal) * qty;
                totalKeuntungan = totalKeuntungan + keuntungan;
            } while (cursor.moveToNext());

            AdapterTransaksi adapterTransaksi = new AdapterTransaksi(this, transaksiModel);
            listTrans.setAdapter(adapterTransaksi);
            listTrans.getAdapter().notifyDataSetChanged();
        }

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalHarga != 0) {
                    if (etTotalBayar.getText().toString().isEmpty()) {
                        Toast.makeText(TransaksiActivity.this, "Masukkan Uang Pembeli", Toast.LENGTH_SHORT).show();
                    } else {
                        double uangPembeli = Double.valueOf(etTotalBayar.getText().toString());
                        double kembalian = uangPembeli - totalHarga;

                        if (kembalian < 0) {
                            Snackbar snackbar = Snackbar.make(view, "Uang Tidak Cukup", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } else {
                            //generate kode transaksi
                            kode_transaksi = TransaksiActivity.getAlphaNumericString(6);
                            db.insertKeuntungan(kode_transaksi, totalKeuntungan);
                            Intent intent = new Intent(TransaksiActivity.this, PembayaranActivity.class);
                            intent.putExtra("harga_total", totalHarga);
                            intent.putExtra("uang_pembeli", uangPembeli);
                            intent.putExtra("uang_kembalian", kembalian);
                            intent.putExtra("total_keuntungan", totalKeuntungan);
                            intent.putExtra("kode_transaksi", kode_transaksi);
                            startActivity(intent);
                            clearTabelKeranjang();
                        }
                    }
                }else{
                    Toast.makeText(TransaksiActivity.this, "Keranjang Masih Kosong !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void clearTabelKeranjang(){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("keranjang", null, null);
    }
    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
